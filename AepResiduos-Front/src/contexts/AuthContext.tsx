import { createContext, useContext, useState, useEffect, ReactNode } from 'react'

interface User {
  id: string
  name: string
  email: string
}

interface AuthContextType {
  user: User | null
  isAuthenticated: boolean
  login: (userData: User, token: string) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    const storedUser = localStorage.getItem('@AepResiduos:user')
    return storedUser ? JSON.parse(storedUser) : null
  })

  const isAuthenticated = !!user

  function login(userData: User, token: string) {
    setUser(userData)
    localStorage.setItem('@AepResiduos:user', JSON.stringify(userData))
    localStorage.setItem('@AepResiduos:token', token)
  }

  function logout() {
    setUser(null)
    localStorage.removeItem('@AepResiduos:user')
    localStorage.removeItem('@AepResiduos:token')
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
} 