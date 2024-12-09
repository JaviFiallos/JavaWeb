<?php
include_once("conexion.php");

class Controlador{

    private $conexion;

    public function __construct()
    {
        $this->conexion = new Conexion();        
    }

    public function obtenerProductos(){
        $sql = "SELECT p.id_producto, p.producto, p.cantidad, b.id_bodega, b.bodega, b.ubicacion, b.ciudad 
        FROM productos p INNER JOIN producto_bodega pb ON p.id_producto = pb.id_producto
        INNER JOIN bodegas b ON pb.id_bodega= b.id_bodega;";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($data);
    }

    public function obtenerSoloProductos(){
        $sql = "SELECT id_producto, producto, cantidad FROM productos";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($data);
    }

    public function obtenerBodegas(){
        $sql = "SELECT id_bodega, bodega, ubicacion, ciudad FROM bodegas";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($data);
    }

    public function buscarPorProducto($nombre){

        $sql = "SELECT p.id_producto, p.producto, p.cantidad, b.id_bodega, b.bodega, b.ubicacion, b.ciudad 
        FROM productos p Left JOIN producto_bodega pb ON p.id_producto = pb.id_producto
        left JOIN bodegas b ON pb.id_bodega= b.id_bodega 
        WHERE p.producto LIKE '%$nombre%'";
        $result = $this->conexion->conectar()->prepare($sql);
        //$result->bindParam(':cedula', $cedula, PDO::PARAM_STR);
        //
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        //
        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo json_encode(["mensaje" => "No se encontraron resultados."]);
        }
    }

    public function buscarPorIdProducto($idProducto){

        $sql = "SELECT id_producto, producto, cantidad FROM productos
        WHERE id_producto = :idProducto";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->bindParam(':idProducto', $idProducto, PDO::PARAM_INT);
        //
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        //
        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo json_encode(["mensaje" => "No se encontraron resultados."]);
        }
    }

    public function buscarPorBodega($idBodega){

        $sql = "SELECT p.id_producto, p.producto, p.cantidad, b.id_bodega, b.bodega, b.ubicacion, b.ciudad 
        FROM productos p INNER JOIN producto_bodega pb ON p.id_producto = pb.id_producto
        INNER JOIN bodegas b ON pb.id_bodega= b.id_bodega 
        WHERE b.id_bodega = :idBodega";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->bindParam(':idBodega', $idBodega, PDO::PARAM_INT);
        //
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        //
        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo json_encode(["mensaje" => "No se encontraron resultados."]);
        }
    }

    public function buscarPorProductoYCiudad($producto, $ciudad){
        $sql = "SELECT p.id_producto, p.producto, p.cantidad, b.id_bodega, b.bodega, b.ubicacion, b.ciudad 
        FROM productos p INNER JOIN producto_bodega pb ON p.id_producto = pb.id_producto
        INNER JOIN bodegas b ON pb.id_bodega= b.id_bodega 
        WHERE p.producto LIKE '%$producto%' AND b.ciudad ='$ciudad'";
        $result = $this->conexion->conectar()->prepare($sql);

        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        //
        if (!empty($data)) {
            echo json_encode($data);
        } else {
            echo json_encode(["mensaje" => "No se encontraron resultados."]);
        }
    }

    public function insertarProducto(){
        try {
            $conexion = $this->conexion->conectar();
            $conexion->beginTransaction();
    
            // Obtener los datos desde el cuerpo de la solicitud
            $producto = isset($_POST['producto']) ? $_POST['producto'] : null;
            $cantidad = isset($_POST['cantidad']) ? $_POST['cantidad'] : null;
    
            // Validación de datos
            if (empty($producto) || empty($cantidad)) {
                //echo json_encode(["mensaje" => "No se ingresaron todos los datos."]);
                return;
            }
    
            $sql = "INSERT INTO productos (producto, cantidad) values (?, ?)";
            $resul = $conexion->prepare($sql);
    
            // Ejecutar la consulta con los valores proporcionados
            $resul->execute([$producto, $cantidad]);
            $conexion->commit();
    
            // Responder al cliente
            echo json_encode(["mensaje" => "Producto creado exitosamente."]);
        } catch (Exception $e) {
            $conexion->rollBack();
            echo json_encode(["mensaje" => "Error al insertar el producto: " . $e->getMessage()]);
        }
    }
    
    

    public function agregarProductoABodega(){
        try {
            $conexion = $this->conexion->conectar();
            $conexion->beginTransaction();
    
            // Obtener los datos desde el cuerpo de la solicitud
            $id_producto = isset($_POST['id_producto']) ? $_POST['id_producto'] : null;
            $id_bodega = isset($_POST['id_bodega']) ? $_POST['id_bodega'] : null;
    
            // Validación de datos
            if (empty($id_producto) || empty($id_bodega)) {
                //echo json_encode(["mensaje" => "No se ingresaron todos los datos."]);
                return;
            }
    
            $sql = "INSERT INTO producto_bodega (id_producto, id_bodega) values (?, ?)";
            $resul = $conexion->prepare($sql);
    
            // Ejecutar la consulta con los valores proporcionados
            $resul->execute([$id_producto, $id_bodega]);
            $conexion->commit();
    
            // Responder al cliente
            echo json_encode(["mensaje" => "Relacion creado exitosamente."]);
        } catch (Exception $e) {
            $conexion->rollBack();
            echo json_encode(["mensaje" => "Error al crear la Relacion: " . $e->getMessage()]);
        }
    }
}

?>