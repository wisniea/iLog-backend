package pl.wit.ilog.wit.form.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IModelRepo extends JpaRepository<Long, FormEntity> {

}
