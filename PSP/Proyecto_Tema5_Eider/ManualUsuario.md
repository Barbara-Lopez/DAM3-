# Proyecto Cuentas Bancarias
## Manual de usuario

- En este proyecto hay Clientes Y un Servidor
- El cliente y el servidor se conectan de forma segura a traves de los certificados 
- Si dos clientes se conectan a la vez puede que haya algún problema con lo que, es mejor que se habra otro 
cliente después de que salga el primer menú.
-  Hay dos menus:

    -  El principal: 
   
        - Inicio sesión  -> Inicias sesión y si no firmas no te deja ir a las operaciones
        - Crear cuenta -> Crea una nueva cuenta para poder luego iniciar sesion
        - Salir -> Sales de la aplicacion
    -  El secundario(Una vez inicias sesión):
   
        -  Crear una cuenta bancaria -> te crea una cuenta y la guarda en el .dat
        - Ver saldo de una cuenta bancaria -> el servidor envia los numeros de cuenta para que el cliente escoja la 
       cuenta y se envia al servidor cifrado, si el descifrado es correcto se devolvera la cuenta con el saldo.
        - Mirar el registro de operaciones hechas -> el servidor te envia un String con todas las operaciones hechas
       hasta el momento, todos los datos se guardan en .txt
        - Ingresar dinero -> como en el de ver saldo se recive y se envia el número de cuenta, pero después de escribir que 
       cantidad de dinero quieres meter en la cuenta tienes que verificar que eres tú el que quiere ingresar el dinero
        - Hacer transferencia -> se envia desde donde a donde quieres hacer la transferencia y como en el apartado de ingresar datos
          tienes que verificar que eres tú el que quiere hacer la transferencia
- Los usuarios puedes verlos en cuentas.png la imagen que está arriba de este documento. La contraseña en todos es 12345
- En el caso de querer otro cliente siempre puedes crear otro con el usuario diferente.