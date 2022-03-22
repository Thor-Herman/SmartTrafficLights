package pt.tecnico.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.tecnico.entities.Device;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
