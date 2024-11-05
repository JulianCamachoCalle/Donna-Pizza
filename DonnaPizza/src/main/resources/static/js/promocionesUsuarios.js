//Desarrollo de anthony

// Obtener lista de promociones y mostrarlas en la tabla
function listarPromocionesUsuarios() {
    fetch('/api/v1/promocionesusuarios')
        .then(response => response.json())
        .then(data => {
            const promocionesUsuariosList = document.getElementById("promocionesUsuarios-list");
            promocionesUsuariosList.innerHTML = ""; // Limpiar tabla antes de cargar datos

            data.forEach(promocionUsuario => {
                let row = `
                    <tr>
                        <td>${promocionUsuario.idPromocionUsuario}</td>
                        <td>${promocionUsuario.idUsuario}</td>
                        <td>${promocionUsuario.idPromocion}</td>
                        <td>${promocionUsuario.fechaAplicacion}</td>
                        <td>${promocionUsuario.estado =='true' ?'Disponible' : 'No Disponible'}</td>
                        <td>
                            <button class="btn btn-warning" onclick="cargarDatosPromocionUsuario(${promocionUsuario.idPromocionUsuario})">Editar</button>
                            <button class="btn btn-danger" onclick="eliminarPromocionUsuario(${promocionUsuario.idPromocionUsuario})">Eliminar</button>
                        </td>
                    </tr>`;
                promocionesUsuariosList.innerHTML += row;
            });
        })
        .catch(error => console.error('Error al listar promociones:', error));
}

document.addEventListener('DOMContentLoaded', listarPromocionesUsuarios);


// Función para guardar una promoción
function guardarPromocionUsuario() {
    const promocionUsuario = {
        idUsuario: document.getElementById("idUsuario").value,
        idPromocion: document.getElementById("idPromocion").value,
        fechaAplicacion: document.getElementById("fechaAplicacion").value,
        estado: document.getElementById("estado").value === 'true'
    };


    fetch('/api/v1/promocionesusuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(promocionUsuario)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al agregar la promoción.",
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: 'Promoción agregada',
                    text: data.mensaje || "La promoción se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#promocionUsuariosModal').modal('hide');
                    listarPromocionesUsuarios();
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al guardar promoción:', error);
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
document.getElementById('fechaAplicacion').value = currentDate;

// Cargar datos al Modal Actualizar
function cargarDatosPromocionUsuario(id) {
    fetch(`/api/v1/promocionesusuarios/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener datos de la promoción');
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
                document.getElementById("idPromocionUsuarioact").value = data.idPromocionUsuario;
                document.getElementById("idUsuarioact").value = data.idUsuario;
                document.getElementById("idPromocionact").value = data.idPromocion;
                document.getElementById("fechaAplicacionact").value = data.fechaAplicacion;
                document.getElementById("estadoact").value = data.estado;

                $('#editarPromocionUsuariosModal').modal('show');
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo cargar los datos de la promoción.',
            });
            console.error('Error al cargar datos de la promoción:', error);
        });
}

// Actualizar
function actualizarPromocionesUsuarios() {
    const promocionUsuarioActualizada = {
        idPromocionUsuario: document.getElementById("idPromocionUsuarioact").value,
        idUsuario: document.getElementById("idUsuarioact").value,
        idPromocion: document.getElementById("idPromocionact").value,
        fechaAplicacion: document.getElementById("fechaAplicacionact").value,
        estado: document.getElementById("estadoact").value === 'true'
    };

    fetch(`/api/v1/promocionesusuarios/${promocionUsuarioActualizada.idPromocionUsuario}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(promocionUsuarioActualizada)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al actualizar la promoción.",
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: 'Promoción actualizada',
                    text: data.mensaje || "La promoción se ha actualizado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    $('#editarPromocionUsuariosModal').modal('hide');
                    listarPromocionesUsuarios();
                });
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: 'No se pudo conectar con el servidor. Intente nuevamente más tarde.',
            });
            console.error('Error al actualizar promoción:', error);
        });
}

// Eliminar
function eliminarPromocionesUsuarios(id) {
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
            fetch(`/api/v1/promocionesusuarios/${id}`, {
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
                            listarPromocionesUsuarios();
                        }
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error de conexión',
                        text: 'No se pudo eliminar la promoción.',
                    });
                    console.error('Error al eliminar promoción:', error);
                });
        }
    });
}

// Exportar a Excel
function exportarExcel() {
    window.location.href = '/excelpromocionesusuarios'; // Asegúrate de que esta ruta esté configurada en tu backend
}
