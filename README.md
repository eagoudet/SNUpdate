Actualizar SN en SAP según regla de negocios

Este proyecto se conecta a una base de datos SAP HANA, ejecuta una consulta para extraer información específica, genera un archivo de resultados en formato TXT y ejecuta un script externo para cargar datos en el sistema.
Características

    Conexión segura a la base de datos SAP HANA.
    Generación de un archivo TXT con resultados específicos.
    Ejecución automatizada de un script (Data Transfer Workbench - DTW).

Requisitos
Tecnológicos

    Java 8 o superior.
    SAP HANA JDBC Driver (agregar a la configuración del proyecto).
    Sistema operativo Windows para ejecutar el script .bat.

Variables de entorno

Configura las siguientes variables de entorno para proteger las credenciales de conexión y los archivos:

    SAP_HANA_URL: URL de la base de datos SAP HANA. Ejemplo: jdbc:sap://1.1.1.1:30015/
    SAP_HANA_USER: Usuario de la base de datos.
    SAP_HANA_PASSWORD: Contraseña del usuario de la base de datos.

Configuración
1. Descargar el proyecto

Clona el repositorio o descarga los archivos del proyecto en tu sistema.
2. Configurar las variables de entorno

Sigue estos pasos según tu sistema operativo:
Windows

    Abre el menú de búsqueda, escribe "Variables de entorno" y selecciona la opción.
    Agrega las siguientes variables del sistema:
        SAP_HANA_URL
        SAP_HANA_USER
        SAP_HANA_PASSWORD
    Reinicia cualquier terminal o aplicación abierta para cargar los cambios.
