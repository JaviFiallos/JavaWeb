package Modelo;

public class Producto {

    private int id_producto;
    private String producto;
    private int cantidad, id_bodega;
    private String bodega, ubicacion, ciudad;

    public Producto(int id_producto, String producto, int cantidad, int id_bodega, String bodega, String ubicacion, String ciudad) {
        this.id_producto = id_producto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.id_bodega = id_bodega;
        this.bodega = bodega;
        this.ubicacion = ubicacion;
        this.ciudad = ciudad;
    }

    public Producto(int id_producto, String producto, int cantidad) {
        this.id_producto = id_producto;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    
    
    public Producto() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_bodega() {
        return id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
