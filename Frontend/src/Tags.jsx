const H1 = ({ children }) => {
  return (
    <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
      {children}
    </h1>
  );
};
export { H1 };

const H2 = ({ children }) => {
  return (
    <h className="scroll-m-20 pb-2 text-3xl font-semibold tracking-tight first:mt-0">
      {children}
    </h>
  );
};
export { H2 };

const H3 = ({ children }) => {
  return (
    <h3 className="scroll-m-20 text-2xl font-semibold tracking-tight">
      {children}
    </h3>
  );
};
export { H3 };

const H4 = ({ children }) => {
  return (
    <h4 className="scroll-m-20 text-xl font-semibold tracking-tight">
      {children}
    </h4>
  );
};
export { H4 };

const P = ({ children }) => {
  return <p className="leading-7 [&:not(:first-child)]:mt-6">{children}</p>;
};
export {P}

const UL = ({children}) => {
    return <ul className="my-6 ml-6 list-disc [&>li]:mt-2">{children}</ul>
}
export {UL}