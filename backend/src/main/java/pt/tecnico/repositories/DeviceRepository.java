package pt.tecnico.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.tecnico.entities.Device;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    Optional<Device> findById(Integer id);

}
