@groovy.transform.TypeChecked
class Product{
  String description
  String id = UUID.randomUUID()
  String dateCreated = new Date()
  String lastUpdate = new Date()
  MaquilaStatus status = MaquilaStatus.CREATED
  Set<Card> cards = new HashSet<Card>()
}
