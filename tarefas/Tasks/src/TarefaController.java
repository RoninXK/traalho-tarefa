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
