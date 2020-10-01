package desafiokotlin_02102020

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertTrue

fun main(){

    var listaAlunos: MutableMap<Int,Aluno> = mutableMapOf()
    var listaProfessores: MutableMap<Int,Professor> = mutableMapOf()
    var listaCurso: MutableMap<Int,Curso> = mutableMapOf()
    var listaMatricula: MutableList<Matricula> = mutableListOf()
    
    var dh1 = DigitalHouseManager(listaAlunos,listaProfessores,listaCurso,listaMatricula)

    println("\n----------------------------------------------------------------\n")
    dh1.registrarCurso("Mobile - POO",1,20)
    dh1.registrarCurso("Mobile - Android",2,20)

    println("\n----------------------------------------------------------------\n")
    dh1.registrarProfessorAdjunto("Igor","Garcia",1,10)
    dh1.registrarProfessorAdjunto("Thaiz","Toledo",3,10)
    dh1.registrarProfessorTitular("Yuri","Garcia",2,"POO")
    dh1.registrarProfessorTitular("Lara","Toledo",4,"Android")
    dh1.registrarProfessorTitular("Igor","Garcia",5,"Marketing Digital")
    dh1.excluirProfessor(5)

    println("\n----------------------------------------------------------------\n")
    for(i in 1..62){
        dh1.matricularAluno("Igor","Garcia",i)
    }

    for(i in 1..21){
        dh1.matricularAluno(i,1)
//        Thread.sleep(5000)
    }
    for(i in 22..42){
        dh1.matricularAluno(i,2)
//        Thread.sleep(5000)
    }

    println("\n----------------------------------------------------------------\n")
    dh1.alocarProfessores(1,2,3)
    dh1.alocarProfessores(2,5,3)
    dh1.alocarProfessores(2,4,1)

    println("\n----------------------------------------------------------------\n")
    dh1.listaCurso.forEach(){
        println("\nCódigo do Curso: ${it.key} " +
                "| Curso: ${it.value.nomeCurso} | qtdAlunos Matriculados: ${it.value.listaAlunosMarticulados.size} | máximo de Alunos permitido: ${it.value.qtdMaxAlunos}" +
                "\n| Prof Titular: ${it.value.professorTitular?.codigoProfessor} | ${it.value.professorTitular?.nome} ${it.value.professorTitular?.sobreNome} | ${it.value.professorTitular?.tempoCasa} | ${it.value.professorTitular?.especialidade}" +
                "\n| Prof Adjunto: ${it.value.professorAdjunto?.codigoProfessor} | ${it.value.professorAdjunto?.nome} ${it.value.professorAdjunto?.sobreNome} | ${it.value.professorAdjunto?.tempoCasa} | ${it.value.professorAdjunto?.qtdHorasMonitoria}")
        println("| Alunos Matriculados: ")
                it.value.listaAlunosMarticulados.forEach(){
                    println(" > Código Aluno: ${it.codigoAluno} | ${it.nome} ${it.sobreNome}")
                }
    }

    println("\n----------------------------------------------------------------\n")
    println(" >> Relação de Matriculas <<")
    dh1.listaMatricula.forEach(){
        println("Aluno: ${it.aluno.codigoAluno} - ${it.aluno.nome} ${it.aluno.sobreNome} | Curso: ${it.curso.nomeCurso} | Data Matricula: ${it.dataMatricula.format(DateTimeFormatter.ofPattern("dd/MM/y HH:mm:ss"))}")
    }

    println("\n----------------------------------------------------------------\n")
    dh1.visualizarCursos() //função adicional para validar crusos existentes.
    dh1.excluirCurso(1)
    dh1.visualizarCursos() //função adicional para validar crusos existentes.

}

class Aluno (nome: String, sobreNome: String, var codigoAluno: Int) :Pessoa(nome, sobreNome){

}

abstract class Pessoa(var nome:String, var sobreNome:String){

}

open class Professor(nome: String, sobreNome: String, var codigoProfessor: Int, var tempoCasa: Int):Pessoa(nome, sobreNome){

}

class ProfessorTitular(nome: String, sobreNome: String, codigoProfessor: Int, tempoCasa: Int, var especialidade: String):Professor(nome, sobreNome, codigoProfessor, tempoCasa) {
    constructor(nome: String, sobreNome: String, codigoProfessor: Int, especialidade: String):this(nome,sobreNome,codigoProfessor,0,especialidade)
}

class ProfessorAdjunto(nome: String, sobreNome: String, codigoProfessor: Int, tempoCasa: Int = 0, var qtdHorasMonitoria: Int):Professor(nome, sobreNome, codigoProfessor, tempoCasa){
    constructor(nome: String, sobreNome: String, codigoProfessor: Int, qtdHorasMonitoria: Int):this(nome,sobreNome,codigoProfessor,0,qtdHorasMonitoria)
}

class Curso (var nomeCurso: String, var codigoCurso: Int, var professorTitular: ProfessorTitular?, var professorAdjunto: ProfessorAdjunto?, var qtdMaxAlunos: Int, var listaAlunosMarticulados: MutableList<Aluno>){

    constructor(nomeCurso: String, codigoCurso: Int, qtdMaxAlunos: Int):this(nomeCurso, codigoCurso,null,null,qtdMaxAlunos, mutableListOf()) {
    }

    fun adicionarUmAluno(aluno:Aluno):Boolean{
        if (listaAlunosMarticulados.size < qtdMaxAlunos) {
            listaAlunosMarticulados.add(aluno)
            return true
        } else {
            return false
        }
    }

    fun excluirUmAluno(aluno:Aluno){
        listaAlunosMarticulados.remove(aluno)
    }
}

class Matricula(var aluno: Aluno, var curso: Curso, var dataMatricula: LocalDateTime = LocalDateTime.now()){

}

class DigitalHouseManager(var listaAlunos: MutableMap<Int,Aluno>, var listaProfessores: MutableMap<Int,Professor>, var listaCurso: MutableMap<Int,Curso>, var listaMatricula: MutableList<Matricula>){

    fun visualizarCursos(){ //função adicional para validar crusos existentes.
        listaCurso.forEach(){
            println("Curso: ${it.key} | ${it.value.nomeCurso} - ${it.value.qtdMaxAlunos}")
        }
    }

    fun registrarCurso(nome:String, codigoCurso: Int, qtdMaxAlunos: Int){
        listaCurso.put(codigoCurso,Curso(nome,codigoCurso,qtdMaxAlunos))
        println("Registra Curso!")
    }

    fun excluirCurso(codigoCurso: Int){
        listaCurso.remove(codigoCurso)
        println("Remove Curso!")
    }

    fun registrarProfessorAdjunto(nome: String, sobrenome: String, codigoProfessor: Int, quantidadeDeHoras: Int){
        listaProfessores.put(codigoProfessor,ProfessorAdjunto(nome,sobrenome,codigoProfessor,quantidadeDeHoras))
        println("Registra Professor Adjunto!")
    }

    fun registrarProfessorTitular(nome: String, sobrenome: String , codigoProfessor: Int, especialidade: String ){
        listaProfessores.put(codigoProfessor,ProfessorTitular(nome,sobrenome,codigoProfessor,especialidade))
        println("Registra Professor Titular!")
    }

    fun excluirProfessor(codigoProfessor: Int){
        listaProfessores.remove(codigoProfessor)
        println("Remove Professor!")
    }

    fun matricularAluno(nome: String , sobreNome: String , codigoAluno: Int){
        listaAlunos.put(codigoAluno,Aluno(nome,sobreNome,codigoAluno))
        println("Matricular Aluno!")
    }

    fun matricularAluno(codigoAluno: Int,  codigoCurso: Int){
        listaAlunos.get(codigoAluno)?.let { aluno ->
            listaCurso.get(codigoCurso)?.let {curso ->
                if(curso.adicionarUmAluno(aluno)){
                    listaMatricula.add(Matricula(aluno,curso))
                    println("A Matricula do Aluno(#${aluno.codigoAluno}) foi realizada no Curso(#${curso.codigoCurso}) escolhido!")
                } else {
                    println("A Matricula do Aluno(#${aluno.codigoAluno}) não pode ser realizada no Curso(#${curso.codigoCurso}) escolhido porque não há mais vagas!")
                }
            } ?: println("Curso não encontrado!")
        } ?: println("Aluno não encontrado!")
    }

    fun alocarProfessores(codigoCurso: Int, codigoProfessorTitular: Int, codigoProfessorAdjunto: Int){
        listaCurso.get(codigoCurso)?.let{curso ->
            listaProfessores.get(codigoProfessorTitular)?.let{
                var profTitular = (it as? ProfessorTitular)
                profTitular?.let {
                    listaProfessores.get(codigoProfessorAdjunto)?.let {
                        var profAdjunto = (it as? ProfessorAdjunto)
                        profAdjunto?.let {
                            curso.professorTitular = profTitular
                            curso.professorAdjunto = profAdjunto
                        } ?: println("Professor Adjunto não encontrado!")
                    } ?: println("Professor Adjunto não encontrado!")
                } ?: println("Professor Titular não encontrado!")
            } ?: println("Professor Titular não encontrado!")
        } ?: println("Curso não encontrado!")
    }
}

