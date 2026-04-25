package edu.comillas.icai.gitt.pat.spring.Practica5.Entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    private Long idUsuario;
    private String correoUsuario;
    private double totalPrecio;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LineaCarrito> lineas = new ArrayList<>();

    public Carrito() {}

    public void recalcularTotal() {
        this.totalPrecio = lineas.stream().mapToDouble(LineaCarrito::getCosteLinea).sum();
    }

    // getters/setters
    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public double getTotalPrecio() { return totalPrecio; }
    public void setTotalPrecio(double totalPrecio) { this.totalPrecio = totalPrecio; }

    public List<LineaCarrito> getLineas() { return lineas; }
    public void setLineas(List<LineaCarrito> lineas) { this.lineas = lineas; }
}