const precios = {
    americana: 29.00,
    pepperoni: 32.00,
    mexicana: 18.00
};

// Función para actualizar la cantidad de pizzas
function actualizarCantidad(producto, cambio) {
    const cantidadElemento = document.getElementById(`cant-${producto}`);
    let cantidad = parseInt(cantidadElemento.textContent);

    // Asegurarse de que la cantidad no sea menor a 1
    cantidad = Math.max(1, cantidad + cambio); 
    cantidadElemento.textContent = cantidad;

    // Recalcular los totales después de actualizar la cantidad
    calcularTotal();
}

// Función para calcular los totales y actualizar los valores en pantalla
function calcularTotal() {
    // Obtener las cantidades actuales de cada pizza
    const cantAmericana = parseInt(document.getElementById('cant-americana').textContent);
    const cantPepperoni = parseInt(document.getElementById('cant-pepperoni').textContent);
    const cantMexicana = parseInt(document.getElementById('cant-mexicana').textContent);

    // Calcular el subtotal
    const subtotal = (precios.americana * cantAmericana) + (precios.pepperoni * cantPepperoni) + (precios.mexicana * cantMexicana);
    
    // Aplicar un descuento del 15%
    const descuento = subtotal * 0.15;

    // Definir el costo de envío
    const envio = 5.00;

    // Calcular el total final
    const total = subtotal - descuento + envio;

    // Actualizar los valores en la interfaz
    document.getElementById('subtotal').textContent = `S/ ${subtotal.toFixed(2)}`;
    document.getElementById('descuento').textContent = `S/ ${descuento.toFixed(2)}`;
    document.getElementById('envio').textContent = `S/ ${envio.toFixed(2)}`;
    document.getElementById('total').textContent = `S/ ${total.toFixed(2)}`;
}

// Llama a la función al cargar la página para inicializar los valores
calcularTotal();