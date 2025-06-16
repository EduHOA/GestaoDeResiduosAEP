import { useScheduling } from '../contexts/SchedulingContext'
import type { Scheduling } from '../contexts/SchedulingContext'
import { format } from 'date-fns'
import { ptBR } from 'date-fns/locale'
import { toast } from 'react-hot-toast'
import { CalendarCheck, MapPin, Landmark, Trash2, CheckCircle2, XCircle } from 'lucide-react'

const statusColors = {
  pendente: 'bg-yellow-100 text-yellow-800',
  concluido: 'bg-green-100 text-green-800',
  cancelado: 'bg-red-100 text-red-800',
}

const statusLabels = {
  pendente: 'Pendente',
  concluido: 'Concluído',
  cancelado: 'Cancelado',
}

export function SchedulingList() {
  const { getUserSchedulings, updateSchedulingStatus, deleteScheduling } = useScheduling()
  const schedulings = getUserSchedulings()

  const handleStatusChange = (id: string, newStatus: Scheduling['status']) => {
    try {
      updateSchedulingStatus(id, newStatus)
      toast.success('Status do agendamento atualizado com sucesso!')
    } catch (error) {
      toast.error('Erro ao atualizar status do agendamento')
    }
  }

  const handleDelete = (id: string) => {
    if (window.confirm('Tem certeza que deseja excluir este agendamento?')) {
      try {
        deleteScheduling(id)
        toast.success('Agendamento excluído com sucesso!')
      } catch (error) {
        toast.error('Erro ao excluir agendamento')
      }
    }
  }

  if (schedulings.length === 0) {
    return (
      <div className="text-center py-8">
        <p className="text-gray-500 dark:text-gray-400">Você ainda não possui agendamentos.</p>
      </div>
    )
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {schedulings.map(scheduling => (
        <div
          key={scheduling.id}
          className="bg-white dark:bg-[#1f2937] rounded-xl shadow p-6 hover:shadow-md transition-shadow"
        >
          <div className="flex flex-col gap-4">
            <div className="flex items-center gap-4">
              <Landmark className="w-8 h-8 text-[#16A34A]" />
              <div>
                <p className="text-lg font-semibold dark:text-white">Empresa</p>
                <p className="dark:text-gray-300">{scheduling.empresa}</p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <MapPin className="w-8 h-8 text-[#FACC15]" />
              <div>
                <p className="text-lg font-semibold dark:text-white">Endereço</p>
                <p className="dark:text-gray-300">{scheduling.endereco}</p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <CalendarCheck className="w-8 h-8 text-[#16A34A]" />
              <div>
                <p className="text-lg font-semibold dark:text-white">Data</p>
                <p className="dark:text-gray-300">
                  {format(new Date(scheduling.data), "dd 'de' MMMM 'de' yyyy", {
                    locale: ptBR,
                  })}
                </p>
              </div>
            </div>

            <div className="flex items-center justify-between">
              <span
                className={`px-3 py-1 rounded-full text-sm font-medium ${
                  statusColors[scheduling.status]
                }`}
              >
                {statusLabels[scheduling.status]}
              </span>

              {scheduling.status === 'pendente' && (
                <div className="flex gap-2">
                  <button
                    onClick={() => handleStatusChange(scheduling.id, 'concluido')}
                    className="p-2 text-green-600 hover:text-green-700 transition"
                    title="Marcar como concluído"
                  >
                    <CheckCircle2 className="w-5 h-5" />
                  </button>
                  <button
                    onClick={() => handleStatusChange(scheduling.id, 'cancelado')}
                    className="p-2 text-red-600 hover:text-red-700 transition"
                    title="Cancelar agendamento"
                  >
                    <XCircle className="w-5 h-5" />
                  </button>
                  <button
                    onClick={() => handleDelete(scheduling.id)}
                    className="p-2 text-gray-600 hover:text-gray-700 transition"
                    title="Excluir agendamento"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      ))}
    </div>
  )
} 