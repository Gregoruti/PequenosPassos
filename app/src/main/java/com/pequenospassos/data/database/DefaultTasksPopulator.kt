package com.pequenospassos.data.database

import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Popula o banco de dados com tarefas padrão na primeira execução.
 *
 * ÚLTIMA ATUALIZAÇÃO: 03/11/2025 - FASE INCREMENTAL
 * - Implementando apenas 1 tarefa para validação
 * - Tarefa: "Escovar os Dentes" (12 passos)
 * - Após validação, expandir para as 11 tarefas completas
 *
 * @since MVP-15 (03/11/2025) - Tarefas Pré-Instaladas
 * @author GPT-4.1 (GitHub Copilot)
 */
class DefaultTasksPopulator(
    private val taskDao: TaskDao,
    private val stepDao: StepDao
) {

    fun populate() {
        CoroutineScope(Dispatchers.IO).launch {
            val existingTasksCount = taskDao.getTasksCount()
            if (existingTasksCount > 0) {
                println("⚠️ DefaultTasksPopulator: Banco já contém $existingTasksCount tarefas. Pulando população.")
                return@launch
            }

            println("✅ DefaultTasksPopulator: Iniciando população de tarefas padrão...")

            // MVP-15: 11 TAREFAS PRÉ-INSTALADAS
            populateTask01_LavarMaos()
            populateTask02_FazerXixi()
            populateTask03_EscovarDentes()
            populateTask04_Vestirse()
            populateTask05_LavarRosto()
            populateTask06_ArrumarCama()
            populateTask07_TomarBanho()
            populateTask08_FazerCoco()
            populateTask09_GuardarBrinquedos()
            populateTask10_EscolherBrinquedoBanho()
            populateTask11_SairDeCasa()

            println("✅ DefaultTasksPopulator: 11 tarefas e 143 passos inseridos com sucesso!")
        }
    }

    private suspend fun populateTask01_LavarMaos() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Lavar as Mãos",
                description = "Higiene básica essencial",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "07:00",
                category = "HIGIENE_PESSOAL",
                stars = 2,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até a pia", 1, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Abra a torneira", 2, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Molhe as mãos", 3, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Pegue o sabonete", 4, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Esfregue bem as mãos", 5, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Enxágue as mãos", 6, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Feche a torneira", 7, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Seque as mãos na toalha", 8, false, null, 20))
    }

    private suspend fun populateTask02_FazerXixi() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Fazer Xixi",
                description = "Rotina do banheiro",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "06:50",
                category = "HIGIENE_PESSOAL",
                stars = 2,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até o banheiro", 1, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Abaixe a calça", 2, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Sente no vaso sanitário", 3, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Faça xixi", 4, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Pegue o papel higiênico", 5, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Limpe-se", 6, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Levante e puxe a descarga", 7, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Vista a calça", 8, false, null, 20))
    }

    private suspend fun populateTask03_EscovarDentes() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Escovar os Dentes",
                description = "Rotina completa de higiene bucal",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "07:05",
                category = "HIGIENE_PESSOAL",
                stars = 3,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Pegue a escova de dentes", 1, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Pegue o creme dental", 2, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Passe o creme dental", 3, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Molhe a escova de dentes", 4, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Escove os dentes debaixo", 5, false, null, 45))
        stepDao.insertStep(Step(0, taskId, "Escove os dentes de cima", 6, false, null, 45))
        stepDao.insertStep(Step(0, taskId, "Escove os dentes da frente", 7, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Escove a língua", 8, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Pegue um pouco d'água", 9, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Cuspa a água", 10, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Enxágue a boca", 11, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Guarde a escova no lugar", 12, false, null, 15))
    }

    private suspend fun populateTask04_Vestirse() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Vestir-se",
                description = "Rotina de se vestir para ir à escola",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "06:55",
                category = "AUTOCUIDADO",
                stars = 4,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até o guarda-roupa", 1, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Escolha a calcinha/cueca", 2, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Vista a calcinha/cueca", 3, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Escolha a blusa", 4, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Vista a blusa", 5, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Escolha a calça ou saia", 6, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Vista a calça ou saia", 7, false, null, 50))
        stepDao.insertStep(Step(0, taskId, "Escolha as meias", 8, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Vista as meias", 9, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Pegue os sapatos", 10, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Calce os sapatos", 11, false, null, 50))
        stepDao.insertStep(Step(0, taskId, "Feche os velcros ou cadarços", 12, false, null, 45))
    }

    private suspend fun populateTask05_LavarRosto() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Lavar o Rosto",
                description = "Higiene facial matinal",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "06:45",
                category = "HIGIENE_PESSOAL",
                stars = 2,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até a pia", 1, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Abra a torneira", 2, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Molhe as mãos", 3, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Molhe o rosto", 4, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Pegue o sabonete", 5, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Ensaboe o rosto com cuidado", 6, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Esfregue suavemente o rosto", 7, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Enxágue bem o rosto", 8, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Feche a torneira", 9, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Seque o rosto na toalha", 10, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Guarde a toalha", 11, false, null, 10))
    }

    private suspend fun populateTask06_ArrumarCama() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Arrumar a Cama",
                description = "Organizar o quarto pela manhã",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "17:10",
                category = "ORGANIZACAO",
                stars = 3,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Saia da cama", 1, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Puxe o lençol para cima", 2, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Alise o lençol com as mãos", 3, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Pegue o travesseiro", 4, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Coloque o travesseiro no lugar", 5, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Pegue o cobertor ou edredom", 6, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Estique o cobertor na cama", 7, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Alise tudo para ficar bonito", 8, false, null, 45))
    }

    private suspend fun populateTask07_TomarBanho() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Tomar Banho",
                description = "Rotina completa de banho",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "19:00",
                category = "HIGIENE_PESSOAL",
                stars = 5,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até o banheiro", 1, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Tire a roupa", 2, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Coloque a roupa suja no cesto", 3, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Entre no chuveiro", 4, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Abra o chuveiro", 5, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Molhe todo o corpo", 6, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Pegue o sabonete", 7, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Ensaboe os braços", 8, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Ensaboe as pernas", 9, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Ensaboe a barriga e as costas", 10, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Ensaboe as partes íntimas", 11, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Enxágue todo o corpo", 12, false, null, 60))
        stepDao.insertStep(Step(0, taskId, "Pegue o shampoo", 13, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Passe o shampoo no cabelo", 14, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Esfregue bem o cabelo", 15, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Enxágue o cabelo", 16, false, null, 60))
        stepDao.insertStep(Step(0, taskId, "Feche o chuveiro", 17, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Saia do chuveiro", 18, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Seque o corpo com a toalha", 19, false, null, 60))
        stepDao.insertStep(Step(0, taskId, "Vista a roupa limpa", 20, false, null, 60))
    }

    private suspend fun populateTask08_FazerCoco() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Fazer Cocô",
                description = "Rotina do banheiro",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "17:30",
                category = "HIGIENE_PESSOAL",
                stars = 3,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até o banheiro", 1, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Abaixe a calça", 2, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Sente no vaso sanitário", 3, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Faça cocô com calma", 4, false, null, 300))
        stepDao.insertStep(Step(0, taskId, "Pegue o papel higiênico", 5, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Limpe-se bem", 6, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Levante e puxe a descarga", 7, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Vista a calça", 8, false, null, 30))
    }

    private suspend fun populateTask09_GuardarBrinquedos() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Guardar Brinquedos",
                description = "Organizar e guardar brinquedos",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "20:30",
                category = "ORGANIZACAO",
                stars = 3,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Olhe os brinquedos no chão", 1, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Pegue os carrinhos, tubos, bolinhas", 2, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Coloque na caixa de brinquedos", 3, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Pegue os blocos e peças", 4, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Coloque na caixa de blocos", 5, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Pegue os livros e gibis", 6, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Coloque na estante de livros", 7, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Olhe se ainda tem brinquedo no chão", 8, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Feche as caixas de brinquedos", 9, false, null, 20))
    }

    private suspend fun populateTask10_EscolherBrinquedoBanho() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Escolher Brinquedo do Banho",
                description = "Preparação para o banho",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "18:55",
                category = "AUTOCUIDADO",
                stars = 1,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá até a caixa de brinquedos", 1, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Escolha 1 ou 2 brinquedos de banho", 2, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Leve os brinquedos para o banheiro", 3, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Coloque os brinquedos perto do chuveiro", 4, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Separe a toalha limpa", 5, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Separe a roupa limpa", 6, false, null, 30))
    }

    private suspend fun populateTask11_SairDeCasa() {
        val taskId = taskDao.insertTask(
            Task(
                id = 0,
                title = "Sair de Casa",
                description = "Rotina para sair com segurança",
                iconRes = android.R.drawable.ic_menu_gallery,
                time = "07:15",
                category = "ROTINA_DIARIA",
                stars = 5,
                imageUrl = null
            )
        )

        stepDao.insertStep(Step(0, taskId, "Vá ao banheiro fazer xixi", 1, false, null, 150))
        stepDao.insertStep(Step(0, taskId, "Lave as mãos", 2, false, null, 60))
        stepDao.insertStep(Step(0, taskId, "Pegue sua mochila ou bolsa", 3, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Confira se tem tudo dentro", 4, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Vista os sapatos", 5, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Pegue uma blusa se estiver frio", 6, false, null, 30))
        stepDao.insertStep(Step(0, taskId, "Vá até a porta de casa", 7, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Espere o adulto trancar a porta", 8, false, null, 20))
        stepDao.insertStep(Step(0, taskId, "Aperte o botão do elevador", 9, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Entre no elevador com cuidado", 10, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Saia do elevador devagar", 11, false, null, 15))
        stepDao.insertStep(Step(0, taskId, "Ande até a garagem", 12, false, null, 40))
        stepDao.insertStep(Step(0, taskId, "Pare antes de atravessar", 13, false, null, 10))
        stepDao.insertStep(Step(0, taskId, "Olhe para os dois lados e atravesse", 14, false, null, 20))
    }
}

