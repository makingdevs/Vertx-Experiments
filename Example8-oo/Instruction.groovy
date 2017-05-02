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

  def toMap(){
    [
      id:id,
      dateCreated: dateCreated,
      lastUpdate: lastUpdate,
      status: status,
      spareId: spareId,
      products: products
    ]
  }

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
}
