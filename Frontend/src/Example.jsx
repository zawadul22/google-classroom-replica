const Example = () => {
  return (
    <div
      className={cn(
        "duration-200 absolute inset-y-0 z-10 hidden h-full w-[--sidebar-width] transition-[left,right,width] ease-linear md:flex",
        side === "left"
          ? "left-0 group-data-[collapsible=offcanvas]:left-[calc(var(--sidebar-width)*-1)]"
          : "right-0 group-data-[collapsible=offcanvas]:right-[calc(var(--sidebar-width)*-1)]",
        // Adjust the padding for floating and inset variants.
        variant === "floating" || variant === "inset"
          ? "top-[var(--header-height)] bottom-[var(--footer-height)] p-2 group-data-[collapsible=icon]:w-[calc(var(--sidebar-width-icon)_+_theme(spacing.4)_+2px)]"
          : "group-data-[collapsible=icon]:w-[--sidebar-width-icon] group-data-[side=left]:border-r group-data-[side=right]:border-l",
        // Handle collapsed state and dynamic width
        collapsible === "icon" && state === "collapsed"
          ? "w-[--sidebar-width-icon]"
          : "w-[--sidebar-width]",
        className
      )}
      {...props}
    >
      <div
        data-sidebar="sidebar"
        className={cn(
          "flex h-full w-full flex-col bg-sidebar",
          "group-data-[variant=floating]:rounded-lg group-data-[variant=floating]:border group-data-[variant=floating]:border-sidebar-border group-data-[variant=floating]:shadow",
          // Hide overflow for collapsible icon state
          collapsible === "icon" && state === "collapsed"
            ? "overflow-hidden w-[--sidebar-width-icon]"
            : "w-full"
        )}
      >
        {children}
      </div>
    </div>
  );
};
