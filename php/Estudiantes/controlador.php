<?php

include_once("conexion.php");

class Controlador
{

    private $conexion;

    public function __construct()
    {
        $this->conexion = new Conexion();
        
    }

    public function obtenerEstudiantes()
    {
        //$this->conexion->conectar();
        $sql = "SELECT e.cedula, e.nombre, e.apellido, e.direccion, e.telefono, m.id_materia, m.materia, m.creditos 
        FROM estudiantes e INNER JOIN materias m ON e.id_materia= m.id_materia;";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($data);
    }

    public function buscarPorCedula($cedula)
    {
        $sql = "SELECT e.cedula, e.nombre, e.apellido, e.direccion, e.telefono, m.id_materia, m.materia, m.creditos 
        FROM estudiantes e INNER JOIN materias m ON e.id_materia= m.id_materia WHERE e.cedula= :cedula;";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->bindParam(':cedula', $cedula, PDO::PARAM_STR);
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

    public function buscarPorNombre($nombre)
    {
        $sql = "SELECT e.cedula, e.nombre, e.apellido, e.direccion, e.telefono, m.id_materia,  m.materia, m.creditos 
        FROM estudiantes e 
        INNER JOIN materias m ON e.id_materia= m.id_materia 
        WHERE e.nombre LIKE '%$nombre%'";
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

    public function buscarPorMateria($idMateria)
    {
        $sql = "SELECT e.cedula, e.nombre, e.apellido, e.direccion, e.telefono, m.id_materia, m.materia, m.creditos 
        FROM estudiantes e 
        INNER JOIN materias m ON e.id_materia= m.id_materia 
        WHERE m.id_materia = :idMateria";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->bindParam(':idMateria', $idMateria, PDO::PARAM_INT);
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

    public function crearEstudiante()
    {
        try {
            $conexion = $this->conexion->conectar();
            $conexion->beginTransaction();
    
            // Obtenemos los datos directamente desde $_POST
            $cedula = $_POST['cedula'] ?? null;
            $nombre = $_POST['nombre'] ?? null;
            $apellido = $_POST['apellido'] ?? null;
            $direccion = $_POST['direccion'] ?? null;
            $telefono = $_POST['telefono'] ?? null;
            $id_materia = $_POST['id_materia'] ?? null;
    
            // Verificamos que todos los datos requeridos estén presentes
            if (
                empty($cedula) || empty($nombre) || empty($apellido) ||
                empty($direccion) || empty($telefono) || empty($id_materia)
            ) {
                echo json_encode(["mensaje" => "No se ingresaron todos los datos."]);
                return;
            }
    
            // Preparamos la consulta SQL para insertar los datos
            $sql = "INSERT INTO estudiantes (cedula, nombre, apellido, direccion, telefono, id_materia) 
                    VALUES ('$cedula', '$nombre', '$apellido', '$direccion', '$telefono', '$id_materia')";
            
            // Ejecutamos la consulta
            $resul = $this->conexion->conectar()->prepare($sql);
            $resul->execute();
    
            $conexion->commit();
            echo json_encode($resul);
        } catch (Exception $e) {
            $conexion->rollBack(); // Usamos rollback en caso de error
            echo json_encode(["mensaje" => "Error al insertar el estudiante: " . $e->getMessage()]);
        }
    }
    
}

//$c = new Controlador();
//$c->obtenerEstudiantes();
