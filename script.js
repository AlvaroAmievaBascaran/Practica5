const API_URL = "/api/carrito";

const productos = {
    1: {
        nombre: "Tabla Softboard 7.0",
        precio: 299
    },
    2: {
        nombre: "Neopreno Pro 4/3 mm",
        precio: 189
    },
    3: {
        nombre: "Pack accesorios",
        precio: 49
    }
};

async function crearCarritoSiNoExiste() {
    let idCarrito = localStorage.getItem("idCarrito");

    if (idCarrito) {
        return idCarrito;
    }

    const respuesta = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            idUsuario: 1,
            correoUsuario: "usuario@waveridershop.com"
        })
    });

    const carrito = await respuesta.json();
    localStorage.setItem("idCarrito", carrito.idCarrito);

    return carrito.idCarrito;
}

async function anadirProducto(idArticulo) {
    const idCarrito = await crearCarritoSiNoExiste();
    const producto = productos[idArticulo];

    await fetch(`${API_URL}/${idCarrito}/linea`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            idArticulo: idArticulo,
            precioUnitario: producto.precio,
            unidades: 1
        })
    });

    alert(producto.nombre + " añadido al carrito");
}

async function cargarCarrito() {
    const idCarrito = localStorage.getItem("idCarrito");

    if (!idCarrito) {
        mostrarCarritoVacio();
        return;
    }

    const respuesta = await fetch(`${API_URL}/${idCarrito}`);

    if (!respuesta.ok) {
        localStorage.removeItem("idCarrito");
        mostrarCarritoVacio();
        return;
    }

    const carrito = await respuesta.json();
    pintarCarrito(carrito);
}

function pintarCarrito(carrito) {
    const tbody = document.getElementById("carrito-body");
    const totalTabla = document.getElementById("total-tabla");
    const subtotalResumen = document.getElementById("subtotal-resumen");
    const totalResumen = document.getElementById("total-resumen");

    if (!tbody) return;

    tbody.innerHTML = "";

    if (!carrito.lineas || carrito.lineas.length === 0) {
        mostrarCarritoVacio();
        return;
    }

    let total = 0;

    carrito.lineas.forEach(linea => {
        const producto = productos[linea.idArticulo];

        const subtotal = linea.precioUnitario * linea.unidades;
        total += subtotal;

        const fila = document.createElement("tr");

        fila.innerHTML = `
      <td>${producto ? producto.nombre : "Producto " + linea.idArticulo}</td>
      <td>${linea.unidades}</td>
      <td>${linea.precioUnitario} €</td>
      <td>${subtotal} €</td>
      <td>
        <button class="btn" onclick="borrarLinea(${linea.idArticulo})">
          Eliminar
        </button>
      </td>
    `;

        tbody.appendChild(fila);
    });

    totalTabla.textContent = total + " €";
    subtotalResumen.textContent = total + " €";
    totalResumen.textContent = total + " €";
}

function mostrarCarritoVacio() {
    const tbody = document.getElementById("carrito-body");

    if (tbody) {
        tbody.innerHTML = `
      <tr>
        <td colspan="5">El carrito está vacío.</td>
      </tr>
    `;
    }

    const totalTabla = document.getElementById("total-tabla");
    const subtotalResumen = document.getElementById("subtotal-resumen");
    const totalResumen = document.getElementById("total-resumen");

    if (totalTabla) totalTabla.textContent = "0 €";
    if (subtotalResumen) subtotalResumen.textContent = "0 €";
    if (totalResumen) totalResumen.textContent = "0 €";
}

async function borrarLinea(idArticulo) {
    const idCarrito = localStorage.getItem("idCarrito");

    if (!idCarrito) {
        return;
    }

    await fetch(`${API_URL}/${idCarrito}/linea/${idArticulo}`, {
        method: "DELETE"
    });

    cargarCarrito();
}

async function borrarCarrito() {
    const idCarrito = localStorage.getItem("idCarrito");

    if (!idCarrito) {
        mostrarCarritoVacio();
        return;
    }

    await fetch(`${API_URL}/${idCarrito}`, {
        method: "DELETE"
    });

    localStorage.removeItem("idCarrito");
    mostrarCarritoVacio();
}

if (document.getElementById("carrito-body")) {
    cargarCarrito();
}