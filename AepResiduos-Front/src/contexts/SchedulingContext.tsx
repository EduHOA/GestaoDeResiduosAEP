import React, { createContext, useContext, useState, useEffect } from 'react'
import { useAuth } from './AuthContext'

export interface Scheduling {
  id: string
  userId: string
  empresa: string
  endereco: string
  data: string
  status: 'pendente' | 'concluido' | 'cancelado'
  createdAt: string
}

interface SchedulingContextData {
  schedulings: Scheduling[]
  addScheduling: (scheduling: Omit<Scheduling, 'id' | 'userId' | 'createdAt' | 'status'>) => void
  updateSchedulingStatus: (id: string, status: Scheduling['status']) => void
  deleteScheduling: (id: string) => void
  getUserSchedulings: () => Scheduling[]
}

const SchedulingContext = createContext<SchedulingContextData>({} as SchedulingContextData)

export function SchedulingProvider({ children }: { children: React.ReactNode }) {
  const [schedulings, setSchedulings] = useState<Scheduling[]>([])
  const { user } = useAuth()

  // Carregar agendamentos do localStorage ao iniciar
  useEffect(() => {
    const storedSchedulings = localStorage.getItem('@AepResiduos:schedulings')
    if (storedSchedulings) {
      setSchedulings(JSON.parse(storedSchedulings))
    }
  }, [])

  // Salvar agendamentos no localStorage sempre que houver mudanças
  useEffect(() => {
    localStorage.setItem('@AepResiduos:schedulings', JSON.stringify(schedulings))
  }, [schedulings])

  const addScheduling = (schedulingData: Omit<Scheduling, 'id' | 'userId' | 'createdAt' | 'status'>) => {
    if (!user) {
      throw new Error('Usuário não está autenticado')
    }

    const newScheduling: Scheduling = {
      ...schedulingData,
      id: crypto.randomUUID(),
      userId: user.id,
      status: 'pendente',
      createdAt: new Date().toISOString(),
    }

    setSchedulings(prev => [...prev, newScheduling])
  }

  const updateSchedulingStatus = (id: string, status: Scheduling['status']) => {
    setSchedulings(prev =>
      prev.map(scheduling =>
        scheduling.id === id ? { ...scheduling, status } : scheduling
      )
    )
  }

  const deleteScheduling = (id: string) => {
    setSchedulings(prev => prev.filter(scheduling => scheduling.id !== id))
  }

  const getUserSchedulings = () => {
    if (!user) return []
    return schedulings.filter(scheduling => scheduling.userId === user.id)
  }

  return (
    <SchedulingContext.Provider
      value={{
        schedulings,
        addScheduling,
        updateSchedulingStatus,
        deleteScheduling,
        getUserSchedulings,
      }}
    >
      {children}
    </SchedulingContext.Provider>
  )
}

export function useScheduling() {
  const context = useContext(SchedulingContext)
  if (!context) {
    throw new Error('useScheduling must be used within a SchedulingProvider')
  }
  return context
} 