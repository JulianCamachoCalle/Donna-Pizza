const registerButton = document.getElementById('register');
const loginButton = document.getElementById('login');
const contenedor = document.getElementById('contenedor');

// Eventos para cambiar entre registro e inicio de sesión
registerButton.addEventListener('click', () => {
    contenedor.classList.add('active');
});

loginButton.addEventListener('click', () => {
    contenedor.classList.remove('active');
});

// Función para guardar un usuario
function guardarUsuario() {
    const usuarios = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        email: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        direccion: document.getElementById("direccion").value,
        rol: rolUsuario,
        contraseña: document.getElementById("contraseña").value,
        fecha_registro: fecha_registroUsuario
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
                    text: data.mensaje || "Hubo un problema al agregar el usuario.",
                });
            } else {
                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Usuario agregado',
                    text: data.mensaje || "El usuario se ha registrado exitosamente.",
                    timer: 2000,
                    showConfirmButton: false
                })
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
const fecha_registroUsuario = currentDate;

const rolUsuario = "USER";

// Función para iniciar sesión
function iniciarSesion() {
    const datosInicio = {
        email: document.getElementsByName("email_login")[0].value,
        contraseña: document.getElementsByName("contraseña_login")[0].value
    };

    fetch('/api/v1/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datosInicio)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.mensaje || "Hubo un problema al iniciar sesión.",
                });
            } else {
                // Si la respuesta es exitosa, redirige o muestra un mensaje
                Swal.fire({
                    icon: 'success',
                    title: 'Inicio de sesión exitoso',
                    text: data.mensaje || "Bienvenido de nuevo.",
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    // Redirige al usuario a la página usuario
                    window.location.href = '../../templates/menuUsuario.html';
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
            console.error('Error al iniciar sesión:', error);
        });
}
