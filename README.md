# Clasificador de Lenguajes
> Por Joaquin Sanchiz
---
Documentación: https://joaquinsanchiz.github.io/Clasificador-de-Lenguaje/    
Es necesario especificar un archivo _"twitter4j.properties"_ con los tokens de acceso a la app de twitter.   
Más informacion: https://themepacific.com/how-to-generate-api-key-consumer-token-access-key-for-twitter-oauth/994/  
Credits: Twitter4J. 

## Introducción
La idea de este proyecto era la de hacer algo versátil que diera buen resultado, buscando la originalidad en el proyecto. Finalmente nos decantamos por un clasificador de lenguajes, algo que ya en clase habíamos comprobado que daría buenos resultados.
Para que este proyecto diera buenos resultados era necesario encontrar un corpus sólido, bien estructurado y abundante, algo que no se encuentra en todos lados. Para no estar perdiendo el tiempo buscando bases de datos mal catalogadas que requerirían mucho tiempo adaptar a nuestro proyecto, decidimos generar el corpus por nuestra cuenta:

## Generación del corpus.
Para la generación del corpus hicimos uso de una de las redes sociales que más se utilizan en estos tiempos: Twitter®. La extracción y minería de datos desde Twitter se realizaría utilizando una API para Java proporcionada por un servicio de terceros denominado twitter4j. Esta API te proporciona acceso a la aplicación desde tu código, y la posibilidad de manipulación de los datos, pudiendo de esta manera realizar búsquedas en la red social, obtener estados de Twitter y poder tratarlos y procesarlos. La idea del funcionamiento del programa es básicamente el siguiente:
1.	Realizar una búsqueda en Twitter.
2.	Obtener una serie de tweets.
3.	Filtrar dichos tweets para obtener un conjunto interesante.
4.	Aplanar y procesar la información para que sea compatible con un fichero csv, con la idea de posteriormente importar dicho fichero al MonkeyLearn.

Por consiguiente y siguiendo el paradigma de programación orientada a objetos, nuestro programa deberá tener:
1.	Un objeto Bot que se encargará de la tarea de obtención del corpus.
2.	Un objeto búsqueda donde se almacenarán las búsquedas a realizar.
3.	Un objeto filtro por el que pasarán los tweets y decidirá si son aptos o no para el procesado.
4.	Un objeto para la escritura en el corpus que aplane y procese la información.
5.	Un objeto para mostrar información acerca del comportamiento del programa por pantalla (texto del tweet, autor, lenguaje y diferentes logs del programa) con la salida formateada en códigos de colores.

Todo esto debe funcionar dentro de unos límites, conocidos como “rate times”. Twitter establece un límite de consultas a su API para evitar un mal uso de dicha interfaz, por lo que se deben establecer paradas y descansos del programa con el motivo de ralentizar el funcionamiento y de realizar menos consultas por instante, para estar dentro de los rate times de Twitter. Para tratar este problema se incluirá un objeto cronómetro para el control de tiempos.

El código se puede encontrar en el siguiente repositorio en GitHub.
La documentación del código se encuentra en la gh-pages del repositorio.
Para evitar un exceso del uso de la API, se almacenarán las entradas del corpus en una tabla hash, con el texto del tweet como key (que se obtiene con el método getText() del objeto Status de la API) y con el lenguaje como value de la hash table (que se obtiene con el método getLang() del objeto Status de la API). De esta manera, si se comprueba si el estado está ya dentro del corpus (dentro de la tabla hash) se evitan interacciones con la API, y se le puede aplicar un tiempo de espera para que se refresquen y se actualicen los tweets de la consulta.
Aclarado todo esto, la consulta que se realizará será: twitter. Esta consulta tiene muchos tweets por segundo, por lo que a nada que se le añada un pequeño tiempo de espera al acabar cada consulta, la siguiente ya tendrá tweets nuevos para procesar.
En el filtro, se filtrarán tweets con contenido sensible, tweets que contengan la palabra “bot” en su nombre de usuario, tweets de menos de 3 palabras, y tweets en árabe, tailandés, chino y algún otro lenguaje extraño. El motivo de filtrar estos tweets es porque contienen caracteres incompatibles en el formato csv, y pueden dar lugar a errores a la hora de importar el fichero en MonkeyLearn. Queremos estar seguros de que las entradas del corpus van a ser correctas antes de poner en funcionamiento el programa, ya que depurar caracteres extraños en un corpus grande puede resultar algo tedioso. Para no estar pendiente del programa, se le ha añadido la funcionalidad de que en caso de que ocurra una excepción, me envíe un mensaje por twitter con el mensaje de la excepción, y también avisará el programa cuando llegue a las 1000 entradas, para la realización de un testeo.
Unas horas más tarde tenemos un corpus bastante sólido con el que ya podemos empezar a trabajar.


