import "./App.css";
import Navigation from "./components/Navigation";
import { SidebarProvider } from "./components/ui/sidebar";
import AppSidebar from "./components/AppSidebar";
import { useEffect, useState } from "react";
import { Route, Routes } from "react-router";
import Home from "./components/Home";
import Taecher from "./components/Teacher";
import Student from "./components/Student";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  
  return (
    <>
      <Navigation sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />
      <SidebarProvider open={sidebarOpen} >
        <AppSidebar sidebarOpen={sidebarOpen} />
        <main>
          <div className="mt-20 ml-6 mr-6">
            {/* <SidebarTrigger/> */}
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/teacher/:index" element={<Taecher />} />
              <Route path="/student/:index2" element={<Student />} />
            </Routes>
          </div>
        </main>
      </SidebarProvider>
    </>
  );
}

export default App;
