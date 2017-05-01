@groovy.transform.Canonical
class Card{
  String id = UUID.randomUUID()
  String dateCreated = new Date()
  String lastUpdate = new Date()
  MaquilaStatus status = MaquilaStatus.CREATED
  String pan
  Map cvv
}
