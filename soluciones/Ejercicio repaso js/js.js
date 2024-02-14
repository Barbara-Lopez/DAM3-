datos=[];
$(document).ready( function name(params) {
    if("datos" in localStorage){
        console.log("ya hay datos");
    }else{
        cogerDatos();
        llenar();
    }


});

function cogerDatos() {
    /*$.ajax({
        url:"datos.json",
        type:"get"
    }).done(function (respuesta) {
        datos=respuesta
    })*/
    $.get("datos.json",function (respuesta) {
        datos=respuesta;
    })
}   

function llenar() {
   localStorage.setItem("datos",JSON.stringify(datos));

}
