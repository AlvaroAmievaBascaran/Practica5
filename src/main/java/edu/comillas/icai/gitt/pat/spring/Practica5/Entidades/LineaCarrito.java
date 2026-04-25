package edu.comillas.icai.gitt.pat.spring.Practica5.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(
        name = "linea_carrito",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_carrito", "id_articulo"})
)
public class LineaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    @JsonBackReference
    private Carrito carrito;

    @Column(name = "id_articulo", nullable = false)
    private Long idArticulo;

    @Column(nullable = false)
    private double precioUnitario;

    @Column(nullable = false)
    private int unidades;

    @Column(nullable = false)
    private double costeLinea;

    public LineaCarrito() {
    }

    public LineaCarrito(Carrito carrito, Long idArticulo, double precioUnitario, int unidades) {
        this.carrito = carrito;
        this.idArticulo = idArticulo;
        this.precioUnitario = precioUnitario;
        this.unidades = unidades;
        recalcularCoste();
    }

    public void recalcularCoste() {
        this.costeLinea = this.precioUnitario * this.unidades;
    }

    // Getters / Setters

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        recalcularCoste();
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
        recalcularCoste();
    }

    public double getCosteLinea() {
        return costeLinea;
    }

    public void setCosteLinea(double costeLinea) {
        this.costeLinea = costeLinea;
    }
}