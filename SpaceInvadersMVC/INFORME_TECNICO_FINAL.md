# Informe Técnico Detallado: Space Invaders MVC

## 1. Introducción y Arquitectura General
Este proyecto implementa el clásico juego "Space Invaders" utilizando el lenguaje **Java** y la biblioteca gráfica **Swing**. La arquitectura fundamental del sistema se basa en el patrón de diseño **MVC (Modelo-Vista-Controlador)**, aplicado de manera estricta para garantizar la separación de responsabilidades, la mantenibilidad del código y la escalabilidad del sistema.

El proyecto se organiza en tres paquetes principales, cada uno correspondiente a una capa del patrón MVC:

*   **`modelo`**: Encapsula la lógica del negocio, las reglas del juego y el estado de la aplicación.
*   **`vista`**: Gestiona la interfaz gráfica de usuario (GUI) y la representación visual de los objetos.
*   **`controlador`**: Actúa como intermediario, gestionando el flujo de información entre el modelo y la vista.

---

## 2. Análisis Detallado de Componentes

### 2.1. El Modelo (`modelo`)
El modelo es el núcleo lógico del juego. Es completamente independiente de la interfaz de usuario; no contiene referencias a clases de Swing ni del paquete `vista`.

*   **`Espacio` (Clase Principal del Modelo)**:
    *   Actúa como el contenedor del mundo del juego.
    *   **Estado**: Mantiene listas de objetos (`Invasor`, `Rayo`, `Muro`) y referencias a la `NaveJugador`. Gestiona variables de estado global como `puntaje`, `nivel`, `creditos` y `gameOver`.
    *   **Lógica**: Implementa métodos cruciales como `actualizarPosiciones()`, `verificarColisiones()`, `moverInvasores()` y `dispararEnemigos()`.
    *   **Independencia**: No sabe cómo se dibujan los objetos, solo conoce sus coordenadas (x, y) y dimensiones.

*   **Entidades del Juego (`NaveJugador`, `Invasor`, `Rayo`, `Muro`)**:
    *   Todas heredan de una clase base o implementan interfaces comunes para posición y dimensiones.
    *   **Patrón Observer**: Estas clases utilizan una interfaz `Observador` para notificar cambios en su posición o estado. Cuando un objeto se mueve en el modelo, llama a `observador.mover(x, y)`.

*   **Interfaz `Observador`**:
    *   Define el contrato que deben cumplir los objetos que deseen ser notificados de cambios en el modelo.
    *   Métodos: `mover(int x, int y)`, `getAncho()`, `getAlto()`, `actualizarEstado(int vida, boolean destruido)`.

### 2.2. La Vista (`vista`)
La vista se encarga exclusivamente de la presentación. Escucha al usuario y dibuja el estado actual del juego.

*   **`PanelPrincipal`**:
    *   Es el contenedor principal (`JPanel`) donde ocurre el juego.
    *   **Gestión del Bucle**: Contiene un `javax.swing.Timer` que dicta el ritmo del juego (aprox. 60 FPS), invocando periódicamente al controlador para actualizar el estado.
    *   **Captura de Eventos**: Implementa `KeyListener` para detectar pulsaciones de teclas (flechas, espacio) y delegarlas al controlador.

*   **Representación Visual (`ImagenObjetoJuego`, `ImagenNave`, etc.)**:
    *   Son componentes Swing (`JLabel` o `JPanel`) que representan visualmente a las entidades del modelo.
    *   **Implementación de Observer**: Aquí reside la clave de la actualización visual. Estas clases implementan la interfaz de notificación para actualizar su posición en pantalla cuando el modelo cambia.

### 2.3. El Controlador (`controlador`)
El controlador orquesta la aplicación. Es el único componente que conoce tanto al Modelo como a la Vista.

*   **`JuegoController`**:
    *   **Inicialización**: Configura el juego y conecta las vistas con sus modelos correspondientes.
    *   **Delegación**: Recibe comandos de la vista (ej. `moverNaveDerecha()`) y los ejecuta en el modelo (`espacio.getNave().moverDerecha()`).
    *   **Ciclo de Actualización**: Su método `actualizarJuego()` es llamado por el Timer de la vista y ejecuta `espacio.actualizarPosiciones()` en el modelo.

*   **`ObservadorController` (Elemento Clave de Refactorización)**:
    *   Interfaz que extiende `modelo.Observador`.
    *   Permite desacoplar la dependencia directa entre Vista y Modelo (ver sección 4).

---

## 3. Procesos Técnicos y Flujo de Datos

### 3.1. El Bucle de Juego (Game Loop)
El juego no es estático; requiere actualizaciones constantes. El flujo es el siguiente:

1.  **Timer (Vista)**: `PanelPrincipal` tiene un `Timer` que dispara un `ActionEvent` cada 16ms (~60 FPS).
2.  **Delegación**: El evento llama a `controller.actualizarJuego()`.
3.  **Actualización Lógica (Modelo)**: El controlador llama a `espacio.actualizarPosiciones()`.
    *   Se mueven los proyectiles y enemigos.
    *   Se detectan colisiones.
    *   Se verifica el estado de victoria/derrota.
4.  **Notificación (Observer)**: Durante la actualización lógica, si un objeto cambia de posición (ej. `nave.mover()`), invoca a `observador.mover(x, y)`.
5.  **Renderizado (Vista)**: El observador (que es un objeto de la vista, ej. `ImagenNave`) recibe la notificación y actualiza su posición en pantalla con `setLocation(x, y)` y `repaint()`.

### 3.2. Manejo de Eventos de Usuario
1.  **Input**: El usuario presiona la tecla `FLECHA_DERECHA`.
2.  **Captura**: El `KeyAdapter` en `PanelPrincipal` detecta el evento `keyPressed`.
3.  **Control**: Se invoca `controller.moverNaveDerecha()`.
4.  **Modelo**: El controlador ejecuta `espacio.getNave().moverDerecha()`.
5.  **Feedback**: La nave cambia su coordenada X en el modelo y notifica a su observador para que la vista se actualice instantáneamente.

---

## 4. Implementación Estricta del Patrón MVC

Uno de los desafíos técnicos más importantes fue asegurar que la **Vista no tuviera dependencias directas del Modelo**, un requisito académico estricto.

### 4.1. El Problema de la Dependencia Cruzada
Inicialmente, las clases de la vista (como `ImagenObjetoJuego`) necesitaban implementar la interfaz `Observador` para recibir actualizaciones. Sin embargo, `Observador` pertenece al paquete `modelo`.
*   **Violación**: `vista.ImagenObjetoJuego` `import modelo.Observador;`
*   Esto rompía la regla de que la vista no debe conocer nada del paquete modelo.

### 4.2. Solución Técnica: Bridge Interface en el Controlador
Para resolver esto, se aplicó un patrón de desacoplamiento utilizando herencia de interfaces a través del controlador.

1.  **Interfaz Puente**: Se creó `controlador.ObservadorController`.
    ```java
    package controlador;
    import modelo.Observador;
    
    public interface ObservadorController extends Observador {
        // Hereda los métodos, pero reside en el paquete controlador
    }
    ```

2.  **Refactorización de la Vista**:
    Las clases visuales ahora implementan esta interfaz del controlador.
    ```java
    package vista;
    import controlador.ObservadorController; // Correcto: Vista -> Controlador
    
    public abstract class ImagenObjetoJuego extends JLabel implements ObservadorController {
        // Implementación de métodos...
    }
    ```

### 4.3. Resultado Arquitectónico
Con esta refactorización, el diagrama de dependencias queda perfectamente alineado con MVC:

*   **Modelo**: Independiente. (No `import vista`, No `import controlador`).
*   **Vista**: Solo depende del Controlador. (`import controlador`, No `import modelo`).
*   **Controlador**: Conoce a ambos para mediarlos. (`import modelo`, `import vista`).

## 5. Diagrama de Clases (Descripción Textual)

*   **`Espacio` (Modelo)** 1 <---> * **`ObjetoJuego` (Modelo)**
    *   Relación de composición: El espacio contiene múltiples objetos de juego.
*   **`ObjetoJuego`** ..> **`Observador` (Modelo)**
    *   Dependencia: Los objetos notifican a su observador.
*   **`ObservadorController` (Controlador)** --|> **`Observador` (Modelo)**
    *   Herencia: La interfaz del controlador extiende la del modelo.
*   **`ImagenObjetoJuego` (Vista)** ..|> **`ObservadorController` (Controlador)**
    *   Implementación: La vista implementa la interfaz del controlador.
*   **`JuegoController`** --> **`Espacio`**
    *   Asociación: El controlador manipula el modelo.
*   **`PanelPrincipal` (Vista)** --> **`JuegoController`**
    *   Asociación: La vista delega acciones al controlador.

## 6. Conclusión
El sistema "Space Invaders MVC" demuestra una aplicación robusta de los principios de ingeniería de software. La separación de capas permite modificar la lógica del juego (ej. cambiar patrones de movimiento de enemigos) sin tocar una sola línea de código de la interfaz gráfica, y viceversa. La solución implementada para la comunicación Vista-Modelo a través de `ObservadorController` asegura el cumplimiento estricto de las restricciones académicas y de diseño.
