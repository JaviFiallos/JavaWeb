<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Estudiante</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <div class="container my-5">
            <h2 class="text-center">Crear Nuevo Producto</h2>
            <form action="productos" method="post" class="border p-4 rounded bg-light">
                <div class="form-group">
                    <label for="producto">Producto</label>
                    <input type="text" name="producto" id="producto" class="form-control" placeholder="Ingrese el producto" required>
                </div>
                <div class="form-group">
                    <label for="cantidad">Cantidad</label>
                    <input type="number" name="cantidad" id="cantidad" class="form-control" placeholder="El codigo de la Materia" required>
                </div>
                <br>
                <button type="submit" class="btn btn-primary btn-block">Guardar Producto</button>
            </form>
        </div>
    </body>
</html>
