import io.vertx.core.json.JsonObject

@groovy.transform.TypeChecked
class Instruction {
  String id = UUID.randomUUID()
  Date dateCreated = new Date()
  Date lastUpdate = new Date()
  MaquilaStatus status = MaquilaStatus.CREATED
  Integer spareId
  List<Product> products
  Integer requestTotal
  String fileName

  JsonObject toJson(){
    new JsonObject(
      id:id,
      dateCreated: dateCreated.toString(),
      lastUpdate: lastUpdate.toString(),
      spareId: spareId,
      products: products,
      requestTotal: requestTotal,
      fileName: fileName
    )
  }

  def toInstruction(JsonObject json){
    InstructionService.fromJson(json)
  }
}
