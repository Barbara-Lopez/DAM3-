# Explicación del proyecto
- Para hacer este proyecto lo primero que hice fué pensar que hera lo necesario para que funcionase.
- Como resultado obtube que tiene que haver un servidor y un cliente.
- Me puse ha mirar como se hacian las ventanas ya que queria un metodo más visual para poder hacerlo más amigable.
- Una vez tenia la parte visual, tuve que pensar como enviar todo lo que escribiesen al servidor.
- Para ello me puse a pensar si udp o tcp.
- Como conclusión, como había hecho una clase Mensaje pense en utilizar tcp, porque se pueden enviar objetos de manera 
que te aseguras de que lo que envias llegue al servidor.
- Deseche la opción del udp porque, el que llegue el paquete al servidor no esta asegurado y porque no es tan seguro como
el tcp y en caso de que el chat sea el de una empresa no creo que a los trabajadores les haga mucha gracia ir perdiendo 
información, sobre todo si lo utilizan para ponerse en contacto por un proyecto importante.
- Para poder hacer grupos y enviarle la información a cada persona que se une a ese grupo, he utilizado multicast, 
de esa manera el servidor utiliza una conexión para poder enviarselo a esos usuarios.
- El servidor envia la información despues de recibir el objeto mensaje y si estás en ese grupo el cliente lo recibe.
