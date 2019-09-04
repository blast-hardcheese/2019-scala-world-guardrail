guardrailTasks in Compile := List(
  ScalaClient(file("petstore.yaml"), imports=List("refined._"))
)
