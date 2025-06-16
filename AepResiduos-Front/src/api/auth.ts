import { API_BASE_URL, API_ENDPOINTS } from './config'

interface RegisterData {
  name: string
  email: string
  password: string
  phone: string
  address: string
}

interface LoginData {
  email: string
  password: string
}

interface AuthResponse {
  token: string
  user: {
    id: string
    name: string
    email: string
  }
}

const defaultHeaders = {
  'Content-Type': 'application/json',
  'Accept': 'application/json'
}

// Função para obter o token do localStorage
function getAuthToken() {
  return localStorage.getItem('@AepResiduos:token')
}

// Função para criar headers com o token de autenticação
function getAuthHeaders() {
  const token = getAuthToken()
  return {
    ...defaultHeaders,
    'Authorization': token ? `Bearer ${token}` : ''
  }
}

export async function registerUser(data: RegisterData) {
  const url = `${API_BASE_URL}${API_ENDPOINTS.register}`
  console.log('URL completa:', url)
  console.log('Headers:', defaultHeaders)
  console.log('Dados sendo enviados:', JSON.stringify(data, null, 2))
  
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify(data),
    })

    console.log('Status da resposta:', response.status)
    console.log('Status Text:', response.statusText)
    console.log('Headers da resposta:', Object.fromEntries(response.headers.entries()))

    // Tenta ler o corpo da resposta independente do status
    const responseText = await response.text()
    console.log('Corpo da resposta:', responseText)

    if (!response.ok) {
      try {
        const errorData = JSON.parse(responseText)
        throw new Error(errorData.message || `Erro ${response.status}: ${response.statusText}`)
      } catch (e) {
        throw new Error(`Erro ${response.status}: ${response.statusText} - ${responseText}`)
      }
    }

    const responseData = JSON.parse(responseText)
    console.log('Registro bem sucedido:', { ...responseData, token: '***' })
    return responseData
  } catch (error) {
    console.error('Erro completo ao registrar:', error)
    throw error
  }
}

export const loginUser = async (email: string, password: string): Promise<AuthResponse> => {
  const url = `${API_BASE_URL}${API_ENDPOINTS.login}`
  const requestBody = { email, password }
  
  console.log('=== DETALHES DA REQUISIÇÃO DE LOGIN ===')
  console.log('URL:', url)
  console.log('Método:', 'POST')
  console.log('Headers:', JSON.stringify(defaultHeaders, null, 2))
  console.log('Body:', JSON.stringify(requestBody, null, 2))
  
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify(requestBody)
    })

    console.log('=== RESPOSTA DO SERVIDOR ===')
    console.log('Status:', response.status)
    console.log('Status Text:', response.statusText)
    console.log('Headers:', JSON.stringify(Object.fromEntries(response.headers.entries()), null, 2))

    // Tenta ler o corpo da resposta independente do status
    const responseText = await response.text()
    console.log('Corpo da resposta:', responseText || '(vazio)')

    if (!response.ok) {
      // Se o corpo estiver vazio, tenta obter mais informações do status
      if (!responseText) {
        if (response.status === 403) {
          throw new Error('Acesso negado. Verifique se suas credenciais estão corretas.')
        }
        throw new Error(`Erro ${response.status}: ${response.statusText || 'Acesso negado'}`)
      }

      try {
        const errorData = JSON.parse(responseText)
        throw new Error(errorData.message || `Erro ${response.status}: ${response.statusText}`)
      } catch (e) {
        throw new Error(`Erro ${response.status}: ${response.statusText} - ${responseText}`)
      }
    }

    const authData = JSON.parse(responseText)
    console.log('Login bem sucedido:', { ...authData, token: '***' })
    
    if (authData.token) {
      localStorage.setItem('@AepResiduos:token', authData.token)
    }
    
    return authData
  } catch (error) {
    console.error('=== ERRO DETALHADO ===')
    console.error('Erro:', error)
    if (error instanceof Error) {
      if (error.message.includes('Failed to fetch')) {
        throw new Error('Não foi possível conectar ao servidor. Verifique se o backend está rodando.')
      }
    }
    throw error
  }
}

export async function logoutUser() {
  const url = `${API_BASE_URL}${API_ENDPOINTS.logout}`
  console.log('Tentando fazer logout...')
  console.log('URL completa:', url)
  console.log('Headers:', getAuthHeaders())
  console.log('Token atual:', getAuthToken())
  
  try {
    console.log('Iniciando requisição de logout...')
    const response = await fetch(url, {
      method: 'POST',
      headers: getAuthHeaders()
    }).catch(error => {
      console.error('Erro na requisição fetch:', error)
      throw new Error(`Não foi possível conectar ao servidor: ${error.message}`)
    })

    console.log('Resposta recebida:', {
      status: response.status,
      statusText: response.statusText,
      headers: Object.fromEntries(response.headers.entries())
    })

    // Tenta ler o corpo da resposta independente do status
    const responseText = await response.text()
    console.log('Corpo da resposta:', responseText)

    if (!response.ok) {
      throw new Error(`Erro ${response.status}: ${response.statusText} - ${responseText}`)
    }

    console.log('Logout bem sucedido')
    return true
  } catch (error) {
    console.error('Erro detalhado ao fazer logout:', error)
    if (error instanceof Error) {
      if (error.message.includes('Failed to fetch')) {
        throw new Error('Não foi possível conectar ao servidor. Verifique se o backend está rodando.')
      }
      throw error
    }
    throw new Error('Erro desconhecido ao fazer logout')
  }
} 