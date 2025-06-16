import { CalendarCheck, Gift } from "lucide-react"
import { SchedulingList } from '../components/SchedulingList'

export function Dashboard() {
  const pontos = 150

  return (
    <div className="container mx-auto px-6 py-10">
      <div className="flex items-center gap-4 mb-8">
        <CalendarCheck className="w-8 h-8 text-[#16A34A]" />
        <h1 className="text-4xl font-bold dark:text-white">Meus Agendamentos</h1>
      </div>

      {/* Pontuação */}
      <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow p-6 mb-10 flex items-center gap-6">
        <Gift className="w-12 h-12 text-[#FACC15]" />
        <div>
          <p className="text-xl font-semibold dark:text-white">Seus Pontos</p>
          <p className="text-3xl font-bold dark:text-white">{pontos}</p>
        </div>
      </div>

      {/* Lista de Agendamentos */}
      <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow p-6">
        <SchedulingList />
      </div>
    </div>
  )
}

