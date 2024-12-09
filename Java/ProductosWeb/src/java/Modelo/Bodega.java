
package Modelo;

public class Bodega {
    
    private int id_bodega;
    private String bodega, ubicacion, ciudad;

    public Bodega() {
    }

    public Bodega(int id_bodega, String bodega, String ubicacion, String ciudad) {
        this.id_bodega = id_bodega;
        this.bodega = bodega;
        this.ubicacion = ubicacion;
        this.ciudad = ciudad;
    }

    public int getId_bodega() {
        return id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
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

    @Override
    public String toString() {
        return this.bodega;
    }
    
    
}
