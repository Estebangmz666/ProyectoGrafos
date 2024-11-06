
# Proyecto de Grafos en JavaFX

### 🚀 Descripción
Este proyecto es una aplicación gráfica desarrollada en JavaFX para representar y manipular grafos, que permite a los usuarios crear nodos y aristas, calcular la distancia mínima entre nodos, y visualizar el grafo de forma dinámica. Los algoritmos de Dijkstra y Floyd-Warshall están implementados para encontrar las rutas más cortas entre nodos, y las aristas pueden representarse como líneas rectas o curvas según la disposición de los nodos, brindando una experiencia visual ordenada.

### 🖥️ Características
- **Creación y eliminación de nodos**: Añade o elimina nodos con facilidad, los cuales se distribuyen en una estructura tipo panal.
- **Conexiones dinámicas**: Las aristas se representan visualmente entre nodos y pueden ser rectas o curvas, ajustándose automáticamente para evitar superposiciones con otros nodos.
- **Peso de las aristas**: El usuario puede asignar un peso a cada arista en la interfaz gráfica.
- **Algoritmos de distancia mínima**:
  - **Dijkstra**: Calcula la ruta más corta desde un nodo inicial a otro nodo específico.
  - **Floyd-Warshall**: Calcula las distancias mínimas entre todos los pares de nodos.
- **Interfaz interactiva**: Modo de agregar arista que permite seleccionar dos nodos y configurar el peso de la arista, activando o desactivando el modo según se requiera.

### 📂 Estructura del Proyecto

- `Controller`: Maneja la lógica de la interfaz y las interacciones del usuario.
- `Model`: Contiene la estructura de datos para el grafo, nodos y aristas, y los algoritmos de Dijkstra y Floyd-Warshall.
- `View`: FXML y recursos visuales para la interfaz gráfica.
- `Service` (opcional): Podría albergar lógica adicional o servicios externos.

### ⚙️ Instalación y Ejecución

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/Estebangmz666/ProyectoGrafos
   ```

2. Abre el proyecto en tu IDE de Java preferido (por ejemplo, IntelliJ o Eclipse).

3. Asegúrate de que tienes JavaFX instalado y configurado. Puedes agregar la librería de JavaFX en tu configuración de proyecto o usar un manejador de dependencias como Maven.

4. Ejecuta el proyecto a través de tu IDE o mediante la terminal:
   ```bash
   mvn clean install
   ```

### 🛠️ Uso

1. **Agregar nodos**: Escribe el nombre de un nodo en el campo de texto y haz clic en “Agregar Nodo”. 
2. **Agregar aristas**: Activa el modo de agregar aristas, selecciona dos nodos, ingresa el peso y observa cómo se dibuja la arista.
3. **Calcular distancias**: Selecciona dos nodos y elige entre Dijkstra o Floyd-Warshall para calcular la distancia mínima.
4. **Eliminar nodos**: Ingresa el nombre del nodo que deseas eliminar y haz clic en “Eliminar Nodo”.

### 📖 Ejemplo de Uso

1. Añade varios nodos al panel.
2. Conecta los nodos con aristas, asignando diferentes pesos.
3. Selecciona dos nodos y calcula la distancia mínima entre ellos usando uno de los algoritmos.
4. Observa cómo se dibuja el camino en la interfaz gráfica.

### 📄 Licencia
Este proyecto está bajo la Licencia GNU/GPL - consulta el archivo [LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) para más detalles.

### 👤 Autor
Desarrollado por Esteban Gómez, Samuel Zuluaga y Juan David Torres.
