const RenderFileContent = ({file}) => {
  const apiUrl = import.meta.env.VITE_BACKEND_URL;
  const fileType = file?.split(".").pop()?.toLowerCase();
  console.log(fileType);
  console.log(file);
    switch (fileType) {
      case "jpg":
      case "jpeg":
      case "png":
      case "gif":
        return (
          <img
            src={`${apiUrl}/classroom/post/getFile?fileName=${file}`}
            alt="File content"
            className="w-[200px]"
          />
        );
      case "pdf":
        return (
          <iframe
            src={`${apiUrl}/classroom/post/getFile?fileName=${file}`}
            title="PDF Viewer"
            className="w-full h-[500px]"
          />
        );
      case "docx":
      case "doc":
        return (
          <iframe
            src={`https://view.officeapps.live.com/op/embed.aspx?src=${encodeURIComponent(
              `${apiUrl}/classroom/post/getFile?fileName=${file}`
            )}`}
            title="Word Document Viewer"
            className="w-full h-[500px]"
          />
        );
      case "xlsx":
      case "xls":
      case "csv":
        return (
          <iframe
            src={`https://view.officeapps.live.com/op/embed.aspx?src=${encodeURIComponent(
              `${apiUrl}/classroom/post/getFile?fileName=${file}`
            )}`}
            title="Excel Document Viewer"
            className="w-full h-[500px]"
          />
        );
      case "pptx":
      case "ppt":
        return (
          <iframe
            src={`https://view.officeapps.live.com/op/embed.aspx?src=${encodeURIComponent(
              `${apiUrl}/classroom/post/getFile?fileName=${file}`
            )}`}
            title="PowerPoint Presentation Viewer"
            className="w-full h-[500px]"
          />
        );
      default:
        return (
          <p className="text-gray-500">Unsupported file type: {fileType}</p>
        );
    }
  };

const FileViewer = ({ file }) => {
  
  return (
    <RenderFileContent file={file} />
  )
};

export default FileViewer;
