package edu.comillas.icai.gitt.pat.spring.Practica5.Repositorio;

import edu.comillas.icai.gitt.pat.spring.Practica5.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {}