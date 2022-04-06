import os
import logging
import logging.handlers
import random

import numpy as np
import skvideo.io
import cv2
import matplotlib.pyplot as plt

import utils

import time
import paho.mqtt.client as paho

# without this some strange errors happen
cv2.ocl.setUseOpenCL(False)
random.seed(123)

from pipeline import (
    PipelineRunner,
    ContourDetection,
    Visualizer,
    CsvWriter,
    VehicleCounter)

# ============================================================================
IMAGE_DIR = "./out"
VIDEO_SOURCE = "input.mp4"
SHAPE = (720, 1280)  # HxW
EXIT_PTS = np.array([
    [[732, 720], [732, 590], [1280, 500], [1280, 720]]
])
total_vehicles = 0
TOPIC = "TRAFFIC_LIGHT_1_DATA"
BROKER = "165.225.92.214"
# ============================================================================

def on_message(client, userdata, message):
    time.sleep(1)
    print("Received message =", str(message.payload.decode("utf-8")))

def train_bg_subtractor(inst, cap, num=500):
    '''
        BG substractor need process some amount of frames to start giving result
    '''
    print ('Training BG Subtractor...')
    i = 0
    for frame in cap:
        inst.apply(frame, None, 0.001)
        i += 1
        if i >= num:
            return cap

def main():
    log = logging.getLogger("main")
    client = paho.Client("traffic_light_1")  # Create client object
    #client.on_message = on_message  # Bind function to callback
    # Connecting to broker
    print("Connecting to broker", BROKER)
    client.connect(BROKER, 8883)
    # Start loop to process received messages
    client.loop_start()
    # Subscribe
    print("Subscribing")
    client.subscribe(TOPIC)
    time.sleep(2)

    # creating exit mask from points, where we will be counting our vehicles
    base = np.zeros(SHAPE + (3,), dtype='uint8')
    exit_mask = cv2.fillPoly(base, EXIT_PTS, (255, 255, 255))[:, :, 0]

    # there is also bgslibrary, that seems to give better BG substruction, but
    # not tested it yet
    bg_subtractor = cv2.createBackgroundSubtractorMOG2(
        history=500, detectShadows=True)

    # processing pipline for programming conviniance
    pipeline = PipelineRunner(pipeline=[
        ContourDetection(bg_subtractor=bg_subtractor,
                         save_image=True, image_dir=IMAGE_DIR),
        # we use y_weight == 2.0 because traffic are moving vertically on video
        # use x_weight == 2.0 for horizontal.
        VehicleCounter(exit_masks=[exit_mask], y_weight=2.0),
        Visualizer(image_dir=IMAGE_DIR),
        CsvWriter(path='./', name='report.csv')
    ], log_level=logging.DEBUG)
    
    # Set up image source
    # You can use also CV2, for some reason it not working for me
    cap = skvideo.io.vreader(VIDEO_SOURCE)

    # skipping 500 frames to train bg subtractor
    train_bg_subtractor(bg_subtractor, cap, num=500)

    _frame_number = -1
    frame_number = -1
    for frame in cap:
        if not frame.any():
            log.error("Frame capture failed, stopping...")
            break

        # real frame number
        _frame_number += 1

        # skip every 2nd frame to speed up processing
        if _frame_number % 2 != 0:
            continue

        # frame number that will be passed to pipline
        # this needed to make video from cutted frames
        frame_number += 1

        # plt.imshow(frame)
        # plt.show()
        # return

        pipeline.set_context({
            'frame': frame,
            'frame_number': frame_number,
        })
        #pipeline.run()
        vehicle_count = pipeline.run()
        if total_vehicles != vehicle_count:
            total_vehicles = vehicle_count
            # Publishing
            print("Publishing")
            client.publish(TOPIC, str(total_vehicles))
    client.disconnect()  # Disconnect
    client.loop_stop()  # Stop loop

# ============================================================================

if __name__ == "__main__":
    log = utils.init_logging()

    if not os.path.exists(IMAGE_DIR):
        log.debug("Creating image directory `%s`...", IMAGE_DIR)
        os.makedirs(IMAGE_DIR)

    main()
