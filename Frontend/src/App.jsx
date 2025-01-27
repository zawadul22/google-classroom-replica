import { useState } from "react";
import { Route, Routes } from "react-router";
import { SidebarProvider } from "./components/ui/sidebar";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

import Navigation from "./components/Navigation";
import AppSidebar from "./components/AppSidebar";
import Home from "./components/Home";
import Taecher from "./components/Teacher";
import Student from "./components/Student";

import "./App.css";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const queryClient = new QueryClient();

  return (
    <>
      <QueryClientProvider client={queryClient}>
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
      </QueryClientProvider>
    </>
  );
}

export default App;
