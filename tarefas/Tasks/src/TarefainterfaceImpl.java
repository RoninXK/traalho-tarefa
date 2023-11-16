

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
