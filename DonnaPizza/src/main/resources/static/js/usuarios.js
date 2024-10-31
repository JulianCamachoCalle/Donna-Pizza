// Obtener lista de usuarios y mostrarlos en la tabla
function listarUsuarios() {
    fetch('/api/v1/usuarios')
        .then(response => response.json())
        .then(data => {
            const usuariosList = document.getElementById("usuarios-list");
            usuariosList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(usuarios => {
                let row = `
                    <tr>
                        <td>${usuarios.id_usuario}</td>
                        <td>${usuarios.nombre}</td>
                        <td>${usuarios.apellido}</td>
                        <td>${usuarios.username}</td>
                        <td>${usuarios.telefono}</td>
                        <td>${usuarios.direccion}</td>
                        <td>${usuarios.rol}</td>
                        <td>${usuarios.password}</td>
                        <td>${usuarios.fecha_registro}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosUsuario(${usuarios.id_usuario})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarUsuario(${usuarios.id_usuario})">Eliminar</button>
                        </td>
                    </tr>`;
                usuariosList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar usuarios:', error));
}

document.addEventListener('DOMContentLoaded', listarUsuarios);

// Función para guardar un usuario
function guardarUsuario() {
    const usuarios = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        username: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        direccion: document.getElementById("direccion").value,
        rol: document.getElementById("rol").value,
        password: document.getElementById("contraseña").value,
        fecha_registro: document.getElementById("fecha_registro").value
    };

    fetch('/api/v1/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarios)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al registrarse.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Bienvenido',
                    text: data.mensaje || "Te registraste exitosamente!",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#usuariosModal').modal('hide'); // Ocultar el modal
                    listarUsuarios(); // Refrescar la lista de usuarios
                });
            }
        })
        .catch(error => {
            // Mostrar mensaje de error en caso de fallo en la conexión
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al guardar usuario:', error);
        });
}

// Obtiene la fecha actual
const today = new Date();
const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0'); // Mes con dos dígitos
const dd = String(today.getDate()).padStart(2, '0'); // Día con dos dígitos

// Formato de la fecha actual en yyyy-mm-dd
const currentDate = `${yyyy}-${mm}-${dd}`;

// Asigna la fecha actual al input
document.getElementById('fecha_registro').value = currentDate;

// Cargar datos al Modal Actualizar
function cargarDatosUsuario(id) {
    fetch(`/api/v1/usuarios/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos del usuario');
            }
            return response.json();
        })
        .then(data => {
            if (data.error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje,
                });
            } else {
                // Cargar datos en el modal
                document.getElementById("id_usuario_editar").value = data.id_usuario;
                document.getElementById("nombre_editar").value = data.nombre;
                document.getElementById("apellido_editar").value = data.apellido;
                document.getElementById("email_editar").value = data.email;
                document.getElementById("telefono_editar").value = data.telefono.replace("+51 ", ""); // Remover el prefijo para editar
                document.getElementById("direccion_editar").value = data.direccion;
                document.getElementById("rol_editar").value = data.rol;
                document.getElementById("contraseña_editar").value = data.password;
                document.getElementById("fecha_registro_editar").value = data.fecha_registro;

                // Mostrar el modal
                $('#editarUsuariosModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos del usuario.',
            });
            console.error('Error al cargar datos del usuario:', error);
        });
}

// Actualizar
function actualizarUsuario() {
    const usuarioActualizado = {
        id: document.getElementById("id_usuario_editar").value,
        nombre: document.getElementById("nombre_editar").value,
        apellido: document.getElementById("apellido_editar").value,
        username: document.getElementById("email_editar").value,
        telefono: document.getElementById("telefono_editar").value,
        direccion: document.getElementById("direccion_editar").value,
        rol: document.getElementById("rol_editar").value,
        password: document.getElementById("contraseña_editar").value,
        fecha_registro: document.getElementById("fecha_registro_editar").value

    };

    fetch(`/api/v1/usuarios/${usuarioActualizado.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarioActualizado)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar el usuario.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Usuario actualizado',
                    text: data.mensaje || "El usuario se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarUsuariosModal').modal('hide'); // Ocultar el modal
                    listarUsuarios(); // Refrescar la lista de usuarios
                });
            }
        })
        .catch(error => {
            // Mostrar mensaje de error en caso de fallo en la conexión
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al actualizar usuario:', error);
        });
}

//Eliminar
function eliminarUsuario(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/v1/usuarios/${id}`, {
                method: 'DELETE'
            })
                .then(response => response.json())
                .then(data => {
                    Swal.fire({
                        icon: data.error ? 'error' : 'success',
                        title: data.error ? 'Error' : 'Éxito',
                        text: data.mensaje,
                    }).then(() => {
                        if (!data.error) {
                            listarUsuarios(); // Refrescar la lista de usuarios
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar el usuario.',
                    });
                    console.error('Error al eliminar usuario:', error);
                });
        }
    });
}

function exportarExcel() {
    window.location.href = '/excelusuarios';
}