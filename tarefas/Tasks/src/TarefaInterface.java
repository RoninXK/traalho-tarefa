// Tarefa

import java.util.List;

public interface TarefaInterface {

    List<Tarefa> obterTodasTarefas();

    Tarefa obterTarefaPorId(Long id);

    Tarefa salvarTarefa(Tarefa tarefa);

    void excluirTarefa(Long id);
}
