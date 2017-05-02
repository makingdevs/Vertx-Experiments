import io.vertx.core.json.JsonObject

class InstructionService {

  static Instruction toInstruction(JsonObject json){
   new Instruction(
                    id: json.getString("id"),
                    fileName: "procesada"
                  )
  }

}
