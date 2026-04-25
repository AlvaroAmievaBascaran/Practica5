package edu.comillas.icai.gitt.pat.spring.Practica5.Repositorio;

import edu.comillas.icai.gitt.pat.spring.Practica5.Entidades.LineaCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineaCarritoRepositorio extends JpaRepository<LineaCarrito, Long> {

    Optional<LineaCarrito> findByCarritoIdCarritoAndIdArticulo(Long idCarrito, Long idArticulo);
}