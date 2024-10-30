function updateQuantity(product, change) {
    const quantityElement = document.getElementById(`qty-${product}`);
    let quantity = parseInt(quantityElement.textContent);
    quantity = Math.max(1, quantity + change); // Evita cantidades menores a 1
    quantityElement.textContent = quantity;

    calculateTotals();
}

function calculateTotals() {
    const americanPrice = 29.00;
    const pepperoniPrice = 32.00; // Precio unitario de Pizza Pepperoni (Familiar)
    const mexicanPrice = 18.00;

    const qtyAmerican = parseInt(document.getElementById('qty-american').textContent);
    const qtyPepperoni = parseInt(document.getElementById('qty-pepperoni').textContent);
    const qtyMexican = parseInt(document.getElementById('qty-mexican').textContent);

    const subtotal = (americanPrice * qtyAmerican) + (pepperoniPrice * qtyPepperoni) + (mexicanPrice * qtyMexican);
    const descuento = subtotal * 0.15; // Ejemplo de descuento
    const envio = 5.00;

    const total = subtotal - descuento + envio;

    document.getElementById('subtotal').textContent = `S/ ${subtotal.toFixed(2)}`;
    document.getElementById('descuento').textContent = `S/ ${descuento.toFixed(2)}`;
    document.getElementById('envio').textContent = `S/ ${envio.toFixed(2)}`;
    document.getElementById('total').textContent = `S/ ${total.toFixed(2)}`;
}

calculateTotals(); // Llama a la función al cargar la página