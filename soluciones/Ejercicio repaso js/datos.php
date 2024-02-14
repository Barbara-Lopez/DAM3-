<?php
$datos=[
    [
        "dni"=>"11111111A",
        "nombre"=>"Pepe",
        "clave"=>"123456",
        "cuentas"=>[
        "numero"=>"11111111111111111111",
        "movimientos"=>
            [
                ["importe"=>100,"fecha"=>"2020-11-19T23:00:00.000Z","tipo"=>"Imposición"],
                ["importe"=>300,"fecha"=>"2020-11-19T23:00:00.000Z","tipo"=>"Reintegro"],
                ["importe"=>200,"fecha"=>"2020-11-19T23:00:00.000Z","tipo"=>"Imposición"],
            ]
        ]
    ]
];
return json_encode($datos);
