import io.vertx.core.json.JsonObject

class InstructionService {

  static Instruction fromJson(def json){
   new Instruction(
                    id: json.id,
                    fileName: "procesada"
                  )
  }

}
