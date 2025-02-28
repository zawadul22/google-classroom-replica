import { useState } from "react";
import { Route, Routes } from "react-router";
import { SidebarProvider } from "./components/ui/sidebar";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

import Navigation from "./components/navabar/Navigation";
import AppSidebar from "./components/sidebar/AppSidebar";
import Home from "./components/Home";
import TabSection from "./components/TabSection";

import "./App.css";
import { ToastContainer } from "react-toastify";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const queryClient = new QueryClient();

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <Navigation
          sidebarOpen={sidebarOpen}
          setSidebarOpen={setSidebarOpen}
        />
        <SidebarProvider open={sidebarOpen} >
          <AppSidebar sidebarOpen={sidebarOpen} />
          <main className="w-full">
            <div className="mt-[72px] ml-6 mr-6">
              {/* <SidebarTrigger/> */}
              <Routes>
                <Route path="/" element={<Home />} loader={<div>Loading..</div>} />
                <Route path="/:type/:index" element={<TabSection />} loader={<div>Loading..</div>}/>
                {/* <Route path="/teacher/:index" element={<Taecher />} />
                <Route path="/student/:index2" element={<Student />} /> */}
              </Routes>
            </div>
          </main>
          <ToastContainer position="bottom-right" pauseOnHover={false} />
        </SidebarProvider>
      </QueryClientProvider>
    </>
  );
}

export default App;
