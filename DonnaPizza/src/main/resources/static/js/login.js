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
        username: document.getElementById("email").value,
        telefono: document.getElementById("telefono").value,
        direccion: document.getElementById("direccion").value,
        rol: rolUsuario,
        password: document.getElementById("contraseña").value,
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
                    timer: 2000,
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
                timer: 2000,
            });
            console.error('Error al guardar usuario:', error);
        });
    setTimeout(() => {
        window.location.href = "/inicioSesion";
    }, 3000);
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

function iniciarSesion() {
    const username = document.querySelector('input[name="emaillogin"]').value;
    const password = document.querySelector('input[name="contraseñalogin"]').value;

    // Validar campos
    if (!username || !password) {
        Swal.fire('Error', 'Por favor, complete todos los campos.', 'error');
        return;
    }

    // Enviar datos al backend
    $.ajax({
        type: 'POST',
        url: '/api/v1/usuarios/login',
        data: {
            username: username,
            password: password
        },
        success: function(response) {
            // Maneja la respuesta, redirige o muestra un mensaje
            if (response.success) {
                window.location.href = '/menuUsuario/${result.id}';
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        },
        error: function() {
            Swal.fire('Error', 'Ocurrió un error en el inicio de sesión.', 'error');
        }
    });
}

