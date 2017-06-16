
println "Escribiendo archivo de salida"

ArrayList<String> lines = ["Linea 1", "Linea 2", "Linea 3", "Linea 4", "Linea 5"]
Integer productsCounter = 4

//Creando archivo
vertx.eventBus().consumer("create.file"){ message ->

  println "========= ${message.body()} ========="
  def fs = vertx.fileSystem()
  fs.createFile("pruebaDeEscritura.txt"){ result ->
    if(result.succeeded()){
      println "Prueba de escritura creado por vertx"
    }
    else{
      println "Archivo de escritura no se ha podido crear"
    }
  }
}


