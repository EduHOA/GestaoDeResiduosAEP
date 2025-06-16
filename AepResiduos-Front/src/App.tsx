import { BrowserRouter } from "react-router-dom"
import { Toaster } from "react-hot-toast"
import { AppRoutes } from "./routes/AppRoutes"
import { AuthProvider } from "./contexts/AuthContext"
import { SchedulingProvider } from "./contexts/SchedulingContext"

export function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <SchedulingProvider>
          <Toaster position="top-right" />
          <AppRoutes />
        </SchedulingProvider>
      </AuthProvider>
    </BrowserRouter>
  )
}
