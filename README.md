# traalho-tarefa
Uma entidade para representar uma tarefa
// Tarefa.java

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private boolean concluida;

    // getters e setters
}
Repositorio
// TarefaRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // Adicione métodos personalizados aqui, se necessário
}
Parte focada para a manipulação das tarefas
// Tarefa

import java.util.List;

public interface TarefaInterface {

    List<Tarefa> obterTodasTarefas();

    Tarefa obterTarefaPorId(Long id);

    Tarefa salvarTarefa(Tarefa tarefa);

    void excluirTarefa(Long id);
}







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefainterfaceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public List<Tarefa> obterTodasTarefas() {
        return tarefaRepository.findAll();
    }

    @Override
    public Tarefa obterTarefaPorId(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    @Override
    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public void excluirTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}
Cria um painel para gerenciar
// TarefaController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public String listarTarefas(Model model) {
        List<Tarefa> tarefas = tarefaService.obterTodasTarefas();
        model.addAttribute("tarefas", tarefas);
        return "tarefas/listar-tarefas";
    }

    @GetMapping("/formulario")
    public String exibirFormularioTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/formulario-tarefa";
    }

    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute Tarefa tarefa) {
        tarefaService.salvarTarefa(tarefa);
        return "redirect:/tarefas";
    }

    @GetMapping("/atualizar/{id}")
    public String exibirFormularioAtualizacao(@PathVariable Long id, Model model) {
        Tarefa tarefa = tarefaService.obterTarefaPorId(id);
        model.addAttribute("tarefa", tarefa);
        return "tarefas/formulario-tarefa";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return "redirect:/tarefas";
    }
}
Dependencias ultilizadas
<!-- pom.xml -->

<!-- Inicializador do Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

Páginas HTML para exibir e interagir com as tarefas
<!-- src/main/resources/templates/tarefas/listar-tarefas.html -->

<!DOCTYPE html>
<html lang="pt" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Lista de Tarefas</title>
</head>
<body>

<h2>Lista de Tarefas</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Descrição</th>
        <th>Concluída</th>
        <th>Ações</th>
    </tr>
    <tr th:each="tarefa : ${tarefas}">
        <td th:text="${tarefa.id}"></td>
        <td th:text="${tarefa.descricao}"></td>
        <td th:text="${tarefa.concluida}"></td>
        <td>
            <a th:href="@{'/tarefas/atualizar/' + ${tarefa.id}}">Atualizar</a>
            |
            <a th:href="@{'/tarefas/excluir/' + ${tarefa.id}}">Excluir</a>
        </td>
    </tr>
</table>

<br>

<a href="/tarefas/formulario">Adicionar Nova Tarefa</a>

</body>
</html>

<!-- src/main/resources/templates/tarefas/formulario-tarefa.html -->

<!DOCTYPE html>
<html lang="pt" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Tarefa</title>
</head>
<body>

<h2>Formulário de Tarefa</h2>

<form action="/tarefas/salvar" method="post" th:object="${tarefa}">
    <input type="hidden" th:field="*{id}"/>

    <label>Descrição:</label>
    <input type="text" th:field="*{descricao}" required/>

    <label>Concluída:</label>
    <input type="checkbox" th:field="*{concluida}"/>

    <br>

    <button type="submit">Salvar Tarefa</button>
</form>

<br>

<a href="/tarefas">Voltar para Lista de Tarefas</a>

</body>
</html>
Propriedades do banco de dados no arquivo application.properties
# src/main/resources/application.properties

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

Agora, você pode executar a aplicação Spring Boot e acessar http://localhost:8080/tarefas para interagir com a lista de tarefas.
