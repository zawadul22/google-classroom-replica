import "./App.css";
import Navigation from "./components/Navigation";
import { SidebarProvider, SidebarTrigger } from "./components/ui/sidebar";
import AppSidebar from "./components/AppSidebar";

function App() {
  return (
    <>
      <Navigation />
      <SidebarProvider>
        <AppSidebar />
        <main>
          <div className="mt-16">
            <SidebarTrigger />
          </div>
        </main>
      </SidebarProvider>
    </>
  );
}

export default App;
