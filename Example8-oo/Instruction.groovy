@groovy.transform.TypeChecked
class Instruction {
  String id = UUID.randomUUID()
  String dateCreated = new Date()
  String lastUpdate = new Date()
  MaquilaStatus status = MaquilaStatus.CREATED
  Integer spareId
  List<Product> products
  Integer requestTotal
  String fileName
}
